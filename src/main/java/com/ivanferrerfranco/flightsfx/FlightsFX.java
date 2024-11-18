package com.ivanferrerfranco.flightsfx;

import com.ivanferrerfranco.flightsfx.utils.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación que inicializa la interfaz gráfica y gestiona el cierre de la aplicación.
 */
public class FlightsFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML principal
        FXMLLoader fxmlLoader = new FXMLLoader(FlightsFX.class.getResource("FXMLMainView.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 600, 400);

        // Aplica la hoja de estilos
        mainScene.getStylesheets().add(getClass().getResource("/com/ivanferrerfranco/flightsfx/css/style.css").toExternalForm());


        // Obtener el controlador principal
        FXMLMainViewController controller = fxmlLoader.getController();

        // Pasar el Stage y la escena principal al controlador
        controller.setStageAndScene(stage, mainScene);

        // Configurar el título de la ventana
        stage.setTitle("Flight Management");
        stage.setScene(mainScene);

        // Capturar el evento de cierre de la ventana
        stage.setOnCloseRequest(event -> {
            // Guardar los cambios en el archivo de vuelos
            FileUtils.saveFlights(controller.getFlights());
        });

        // Mostrar la ventana principal
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
