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
 * Se encarga de manejar la lógica de la interfaz gráfica y aplicar los filtros requeridos.
 */
public class FXMLMainViewController {

    // Componentes de la interfaz gráfica (FXML)
    @FXML
    private TableView<Flight> tableFlights;
    @FXML
    private TableColumn<Flight, String> colFlightNumber;
    @FXML
    private TableColumn<Flight, String> colDestination;
    @FXML
    private TableColumn<Flight, LocalDateTime> colDeparture;
    @FXML
    private TableColumn<Flight, LocalTime> colDuration;

    @FXML
    private TextField txtFlightNumber;
    @FXML
    private TextField txtDestination;
    @FXML
    private TextField txtDeparture;
    @FXML
    private TextField txtDuration;

    @FXML
    private ChoiceBox<String> choiceFilter;

    private ObservableList<Flight> flights;
    private Stage stage;
    private Scene mainScene;

    /**
     * Devuelve la lista completa de vuelos (sin filtrar).
     * @return Lista de vuelos.
     */
    public ObservableList<Flight> getFlights() {
        return flights;
    }

    /**
     * Inicializa el controlador, configurando las columnas y cargando los datos iniciales.
     */
    @FXML
    private void initialize() {
        colFlightNumber.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDeparture.setCellValueFactory(new PropertyValueFactory<>("departureDateTime"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        colDeparture.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.format(formatter));
            }
        });

        flights = FXCollections.observableArrayList(FileUtils.loadFlights());
        tableFlights.setItems(flights);

        updateChoiceBox();
    }

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

    @FXML
    private void addFlight() {
        try {
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

            Flight newFlight = new Flight(flightNumber, destination, departure, duration);
            flights.add(newFlight);
            FileUtils.saveFlights(flights);
            updateChoiceBox();

            txtFlightNumber.clear();
            txtDestination.clear();
            txtDeparture.clear();
            txtDuration.clear();

            MessageUtils.showMessage("Vuelo agregado exitosamente.");
        } catch (Exception e) {
            MessageUtils.showError("Error al agregar el vuelo: " + e.getMessage());
        }
    }

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
                                .filter(flight -> flight.getDepartureDateTime().isAfter(LocalDateTime.now())) // Filtrar vuelos futuros
                                .sorted(Comparator.comparing(Flight::getDepartureDateTime)) // Ordenar por fecha de salida
                                .limit(5) // Limitar a 5 vuelos
                                .toList()
                );

                // Validar si hay vuelos futuros
                if (nextFlights.isEmpty()) {
                    MessageUtils.showError("No hay vuelos próximos disponibles.");
                } else {
                    tableFlights.setItems(nextFlights); // Actualizar la tabla con los próximos vuelos
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

    public void setStageAndScene(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
    }
}
