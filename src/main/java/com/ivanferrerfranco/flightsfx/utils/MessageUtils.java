package com.ivanferrerfranco.flightsfx.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Clase de utilidad para mostrar mensajes emergentes (alertas) al usuario.
 * Contiene métodos estáticos para mostrar diferentes tipos de alertas.
 */
public class MessageUtils {

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param message El texto del mensaje de error que se mostrará.
     */
    public static void showError(String message) {
        // Crear una alerta de tipo ERROR
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error"); // Título de la ventana de la alerta
        alert.setHeaderText(null); // Sin texto en el encabezado
        alert.setContentText(message); // Contenido del mensaje
        alert.showAndWait(); // Mostrar la alerta y esperar la interacción del usuario
    }

    /**
     * Muestra un mensaje informativo al usuario.
     *
     * @param message El texto del mensaje informativo que se mostrará.
     */
    public static void showMessage(String message) {
        // Crear una alerta de tipo INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información"); // Título de la ventana de la alerta
        alert.setHeaderText(null); // Sin texto en el encabezado
        alert.setContentText(message); // Contenido del mensaje
        alert.showAndWait(); // Mostrar la alerta y esperar la interacción del usuario
    }
}
