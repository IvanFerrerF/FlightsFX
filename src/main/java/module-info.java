module com.ivanferrerfranco.flightsfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.ivanferrerfranco.flightsfx to javafx.fxml;
    opens com.ivanferrerfranco.flightsfx.model to javafx.base;

    exports com.ivanferrerfranco.flightsfx;
}