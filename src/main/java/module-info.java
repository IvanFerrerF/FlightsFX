/**
 * Módulo principal para la aplicación de gestión de vuelos (FlightsFX).
 * Este módulo gestiona las dependencias necesarias para la ejecución de la aplicación.
 *
 * Requiere las bibliotecas de JavaFX y ControlsFX para la interfaz gráfica.
 * Exporta el paquete principal y abre los paquetes específicos para su uso en JavaFX.
 */
module com.ivanferrerfranco.flightsfx {
    // Requiere las bibliotecas JavaFX necesarias para la interfaz de usuario
    requires javafx.controls;
    requires javafx.fxml;

    // Requiere ControlsFX para componentes avanzados de la UI
    requires org.controlsfx.controls;

    // Abre el paquete com.ivanferrerfranco.flightsfx para que sea accesible desde javafx.fxml
    opens com.ivanferrerfranco.flightsfx to javafx.fxml;

    // Abre el paquete com.ivanferrerfranco.flightsfx.model para que sea accesible desde javafx.base
    opens com.ivanferrerfranco.flightsfx.model to javafx.base;

    // Exporta el paquete principal para que otros módulos puedan acceder a él
    exports com.ivanferrerfranco.flightsfx;
}
