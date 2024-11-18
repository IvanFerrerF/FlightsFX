package com.ivanferrerfranco.flightsfx;

import com.ivanferrerfranco.flightsfx.model.Flight;
import com.ivanferrerfranco.flightsfx.utils.FileUtils;
import com.ivanferrerfranco.flightsfx.utils.MessageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Controlador principal para la gestión de vuelos en la aplicación FlightsFX.
 * Este controlador gestiona la lógica de la interfaz gráfica, incluyendo
 * la adición, eliminación, filtrado y visualización de vuelos.
 */
public class FXMLMainViewController {

    // Componentes de la interfaz gráfica definidos en el archivo FXML
    @FXML
    private TableView<Flight> tableFlights; // Tabla que muestra los vuelos
    @FXML
    private TableColumn<Flight, String> colFlightNumber; // Columna para el número de vuelo
    @FXML
    private TableColumn<Flight, String> colDestination; // Columna para el destino
    @FXML
    private TableColumn<Flight, LocalDateTime> colDeparture; // Columna para la fecha y hora de salida
    @FXML
    private TableColumn<Flight, LocalTime> colDuration; // Columna para la duración del vuelo

    @FXML
    private TextField txtFlightNumber; // Campo de texto para el número de vuelo
    @FXML
    private TextField txtDestination; // Campo de texto para el destino
    @FXML
    private TextField txtDeparture; // Campo de texto para la fecha y hora de salida
    @FXML
    private TextField txtDuration; // Campo de texto para la duración

    @FXML
    private ChoiceBox<String> choiceFilter; // Menú desplegable para los filtros

    // Lista observable que contiene los vuelos cargados
    private ObservableList<Flight> flights;

    // Referencias al Stage principal y a la escena principal
    private Stage stage;
    private Scene mainScene;

    /**
     * Devuelve la lista completa de vuelos cargados.
     *
     * @return Lista de vuelos.
     */
    public ObservableList<Flight> getFlights() {
        return flights;
    }

    /**
     * Método de inicialización del controlador.
     * Configura las columnas de la tabla, formatea la fecha de salida
     * y carga los datos iniciales desde el archivo.
     */
    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla
        colFlightNumber.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departureDateTime"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Formatear la fecha en la tabla
        colDeparture.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.format(formatter));
            }
        });

        // Cargar los datos iniciales
        flights = FXCollections.observableArrayList(FileUtils.loadFlights());
        tableFlights.setItems(flights);

        // Configurar las opciones del filtro
        updateChoiceBox();
    }

    /**
     * Actualiza las opciones del filtro en el menú desplegable.
     */
    private void updateChoiceBox() {
        ObservableList<String> filterOptions = FXCollections.observableArrayList(
                "Show all flights",
                "Show flights to currently selected city",
                "Show long flights",
                "Show next 5 flights",
                "Show flight duration average"
        );
        choiceFilter.setItems(filterOptions);
        choiceFilter.setValue("Show all flights");
    }

    /**
     * Agrega un vuelo a la tabla y al archivo.
     * Valida los campos y muestra mensajes de error si hay problemas.
     */
    @FXML
    private void addFlight() {
        try {
            // Validar y obtener los datos del vuelo
            String flightNumber = txtFlightNumber.getText().trim();
            String destination = txtDestination.getText().trim();
            String departureText = txtDeparture.getText().trim();
            String durationText = txtDuration.getText().trim();

            if (flightNumber.isEmpty() || destination.isEmpty() || departureText.isEmpty() || durationText.isEmpty()) {
                MessageUtils.showError("Por favor, completa todos los campos.");
                return;
            }

            LocalDateTime departure = LocalDateTime.parse(departureText, DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
            LocalTime duration = LocalTime.parse(durationText, DateTimeFormatter.ofPattern("H:mm"));

            // Crear el vuelo y agregarlo
            Flight newFlight = new Flight(flightNumber, destination, departure, duration);
            flights.add(newFlight);
            FileUtils.saveFlights(flights);
            updateChoiceBox();

            // Limpiar los campos
            txtFlightNumber.clear();
            txtDestination.clear();
            txtDeparture.clear();
            txtDuration.clear();

            MessageUtils.showMessage("Vuelo agregado exitosamente.");
        } catch (Exception e) {
            MessageUtils.showError("Error al agregar el vuelo: " + e.getMessage());
        }
    }

    /**
     * Elimina el vuelo seleccionado de la tabla y del archivo.
     * Muestra un mensaje de error si no se selecciona ningún vuelo.
     */
    @FXML
    private void deleteFlight() {
        Flight selectedFlight = tableFlights.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            flights.remove(selectedFlight);
            FileUtils.saveFlights(flights);
            updateChoiceBox();
            MessageUtils.showMessage("Vuelo eliminado correctamente.");
        } else {
            MessageUtils.showError("No se ha seleccionado ningún vuelo.");
        }
    }

    /**
     * Aplica el filtro seleccionado a la tabla de vuelos.
     * Los filtros incluyen mostrar todos los vuelos, vuelos largos,
     * próximos 5 vuelos y promedio de duración.
     */
    @FXML
    private void applyFilter() {
        String selectedFilter = choiceFilter.getValue();
        switch (selectedFilter) {
            case "Show all flights":
                tableFlights.setItems(flights);
                break;
            case "Show flights to currently selected city":
                Flight selectedFlight = tableFlights.getSelectionModel().getSelectedItem();
                if (selectedFlight == null) {
                    MessageUtils.showError("No se ha seleccionado ningún vuelo.");
                    return;
                }
                ObservableList<Flight> cityFlights = FXCollections.observableArrayList(
                        flights.stream().filter(f -> f.getDestination().equals(selectedFlight.getDestination())).toList()
                );
                tableFlights.setItems(cityFlights);
                break;
            case "Show long flights":
                ObservableList<Flight> longFlights = FXCollections.observableArrayList(
                        flights.stream().filter(f -> f.getDuration().isAfter(LocalTime.of(3, 0))).toList()
                );
                tableFlights.setItems(longFlights);
                break;
            case "Show next 5 flights":
                ObservableList<Flight> nextFlights = FXCollections.observableArrayList(
                        flights.stream()
                                .filter(flight -> flight.getDepartureDateTime().isAfter(LocalDateTime.now()))
                                .sorted(Comparator.comparing(Flight::getDepartureDateTime))
                                .limit(5)
                                .toList()
                );
                if (nextFlights.isEmpty()) {
                    MessageUtils.showError("No hay vuelos próximos disponibles.");
                } else {
                    tableFlights.setItems(nextFlights);
                }
                break;
            case "Show flight duration average":
                double avgMinutes = flights.stream()
                        .mapToDouble(f -> f.getDuration().getHour() * 60 + f.getDuration().getMinute())
                        .average()
                        .orElse(0);
                MessageUtils.showMessage("Duración media: " + (int) (avgMinutes / 60) + "h " + (int) (avgMinutes % 60) + "m");
                break;
        }
    }

    /**
     * Muestra un gráfico circular con los datos de los vuelos agrupados por destino.
     */
    @FXML
    private void showChart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChartView.fxml"));
            Scene chartScene = new Scene(loader.load());
            ChartViewController chartController = loader.getController();
            chartController.initializeData(flights, stage, () -> stage.setScene(mainScene));
            stage.setScene(chartScene);
        } catch (IOException e) {
            MessageUtils.showError("Error al cargar el gráfico: " + e.getMessage());
        }
    }

    /**
     * Configura el Stage y la escena principal del controlador.
     *
     * @param stage      Stage principal.
     * @param mainScene  Escena principal de la aplicación.
     */
    public void setStageAndScene(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
    }
}
