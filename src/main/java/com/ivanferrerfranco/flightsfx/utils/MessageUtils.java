package com.ivanferrerfranco.flightsfx.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Clase de utilidad para mostrar mensajes emergentes (alertas) al usuario.
 * Proporciona métodos estáticos para mostrar diferentes tipos de alertas,
 * como mensajes de error o mensajes informativos.
 */
public class MessageUtils {

    /**
     * Constructor vacío de la clase {@link MessageUtils}.
     * <p>
     * Este constructor vacío es necesario para crear una instancia de la clase,
     * aunque no se utiliza en esta clase ya que los métodos son estáticos.
     * <p>
     * Este constructor es principalmente utilizado para cumplir con la convención de Javadoc.
     */
    public MessageUtils() {
        // Constructor vacío. No se necesita ninguna implementación aquí.
    }

    /**
     * Muestra un mensaje de error al usuario.
     * Crea una alerta con tipo {@link Alert.AlertType#ERROR} y el texto proporcionado.
     *
     * @param message El texto del mensaje de error que se mostrará.
     *                Este texto se muestra como contenido principal de la alerta.
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
     * Crea una alerta con tipo {@link Alert.AlertType#INFORMATION} y el texto proporcionado.
     *
     * @param message El texto del mensaje informativo que se mostrará.
     *                Este texto se muestra como contenido principal de la alerta.
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
