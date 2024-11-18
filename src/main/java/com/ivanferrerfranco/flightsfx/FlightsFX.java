package com.ivanferrerfranco.flightsfx;

import com.ivanferrerfranco.flightsfx.utils.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación que inicializa la interfaz gráfica y gestiona el ciclo de vida de la aplicación.
 * <p>
 * Esta clase extiende {@link Application} y contiene el método {@code start} que configura la ventana principal,
 * carga el archivo FXML y aplica estilos CSS. También captura el evento de cierre para guardar los cambios.
 */
public class FlightsFX extends Application {

    /**
     * Método de inicio de la aplicación de JavaFX.
     * <p>
     * Este método carga el archivo FXML principal, configura la escena y el {@link Stage},
     * aplica los estilos CSS y maneja el evento de cierre para guardar los datos en el archivo.
     *
     * @param stage El escenario principal donde se cargará la interfaz gráfica.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML principal
        FXMLLoader fxmlLoader = new FXMLLoader(FlightsFX.class.getResource("FXMLMainView.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 600, 400);

        // Aplica la hoja de estilos CSS
        mainScene.getStylesheets().add(getClass().getResource("/com/ivanferrerfranco/flightsfx/css/style.css").toExternalForm());

        // Obtener el controlador principal desde el FXML
        FXMLMainViewController controller = fxmlLoader.getController();

        // Pasar el Stage y la escena principal al controlador
        controller.setStageAndScene(stage, mainScene);

        // Configurar el título de la ventana principal
        stage.setTitle("Flight Management");
        stage.setScene(mainScene);

        // Capturar el evento de cierre para guardar los datos
        stage.setOnCloseRequest(event -> {
            // Guardar los cambios en el archivo de vuelos
            FileUtils.saveFlights(controller.getFlights());
        });

        // Mostrar la ventana principal
        stage.show();
    }

    /**
     * Método principal que lanza la aplicación.
     * <p>
     * Este método llama al método {@code launch()} de {@link Application}, que inicia la aplicación de JavaFX.
     *
     * @param args Argumentos de línea de comandos (actualmente no utilizados).
     */
    public static void main(String[] args) {
        launch();
    }
}
