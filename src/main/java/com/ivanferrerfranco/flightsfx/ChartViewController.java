package com.ivanferrerfranco.flightsfx;

import com.ivanferrerfranco.flightsfx.model.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.stream.Collectors;

/**
 * Controlador para la vista del gráfico circular.
 * Esta clase se encarga de gestionar el comportamiento y la lógica
 * de la vista que muestra un gráfico circular de destinos de vuelos.
 */
public class ChartViewController {

    // Referencia al componente gráfico PieChart definido en el archivo FXML
    @FXML
    private PieChart pieChart;

    // Nodo raíz del archivo FXML
    @FXML
    private AnchorPane rootPane;

    // Referencia al Stage actual para realizar configuraciones o cambios en la ventana
    private Stage stage;

    // Lista observable que contiene los datos de los vuelos
    private ObservableList<Flight> flights;

    // Callback que define la acción a realizar cuando el usuario desea regresar a la vista anterior
    private Runnable goBackCallback;

    /**
     * Inicializa los datos necesarios para la vista del gráfico circular.
     * Configura el gráfico con los datos de vuelos agrupados por destino,
     * aplica la hoja de estilos CSS, y establece la acción de volver a la vista principal.
     *
     * @param flights         Lista de vuelos que se mostrarán en el gráfico.
     * @param stage           Stage principal donde se encuentra esta vista.
     * @param goBackCallback  Acción que se ejecutará al presionar el botón de regreso.
     */
    public void initializeData(ObservableList<Flight> flights, Stage stage, Runnable goBackCallback) {
        // Asigna los parámetros recibidos a las variables del controlador
        this.flights = flights;
        this.stage = stage;
        this.goBackCallback = goBackCallback;

        // Procesa los datos de vuelos para agruparlos por destino y contar cuántos vuelos hay por cada uno
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                flights.stream()
                        // Agrupa los vuelos por su destino y cuenta cuántos hay por cada uno
                        .collect(Collectors.groupingBy(Flight::getDestination, Collectors.counting()))
                        .entrySet().stream()
                        // Crea un objeto PieChart.Data para cada destino con su respectivo conteo
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .toList()
        );

        // Asigna los datos procesados al gráfico circular
        pieChart.setData(pieChartData);

        // Establece un título descriptivo para el gráfico
        pieChart.setTitle("Flights By Destination");

        // Aplica la hoja de estilos al nodo raíz
        rootPane.getStylesheets().add(getClass().getResource("/com/ivanferrerfranco/flightsfx/css/style.css").toExternalForm());
    }

    /**
     * Método llamado cuando el usuario presiona el botón "Back".
     * Este método ejecuta el callback configurado para regresar a la vista principal.
     */
    @FXML
    private void goBack() {
        // Ejecuta la acción definida en el callback para regresar
        goBackCallback.run();
    }
}
