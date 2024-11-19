package com.ivanferrerfranco.flightsfx.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Clase que representa un vuelo con sus detalles.
 * Contiene información como el número de vuelo, destino,
 * fecha y hora de salida, y duración del vuelo.
 */
public class Flight {

    // Atributos de la clase

    /** Número del vuelo, identificador único del vuelo. */
    private String flightNumber;

    /** Destino del vuelo, indicando la ciudad o aeropuerto de llegada. */
    private String destination;

    /** Fecha y hora de salida del vuelo. */
    private LocalDateTime departureDateTime;

    /** Duración del vuelo expresada en horas y minutos. */
    private LocalTime duration;

    // Constructores

    /**
     * Constructor que inicializa un vuelo con solo el número de vuelo.
     * Útil para casos en los que el resto de los datos no están disponibles.
     *
     * @param flightNumber Número del vuelo.
     */
    public Flight(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Constructor que inicializa un vuelo con todos sus detalles.
     *
     * @param flightNumber       Número del vuelo.
     * @param destination        Destino del vuelo.
     * @param departureDateTime  Fecha y hora de salida del vuelo.
     * @param duration           Duración del vuelo.
     */
    public Flight(String flightNumber, String destination, LocalDateTime departureDateTime, LocalTime duration) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.duration = duration;
    }

    // Métodos getter y setter para acceder y modificar los atributos.

    /**
     * Devuelve el número del vuelo.
     *
     * @return El número del vuelo.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Establece el número del vuelo.
     *
     * @param flightNumber El número del vuelo.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Devuelve el destino del vuelo.
     *
     * @return El destino del vuelo.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Establece el destino del vuelo.
     *
     * @param destination El destino del vuelo.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Devuelve la fecha y hora de salida del vuelo.
     *
     * @return La fecha y hora de salida del vuelo.
     */
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * Establece la fecha y hora de salida del vuelo.
     *
     * @param departureDateTime La fecha y hora de salida del vuelo.
     */
    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    /**
     * Devuelve la duración del vuelo.
     *
     * @return La duración del vuelo.
     */
    public LocalTime getDuration() {
        return duration;
    }

    /**
     * Establece la duración del vuelo.
     *
     * @param duration La duración del vuelo.
     */
    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    /**
     * Representa el vuelo como una cadena de texto legible.
     *
     * @return Una cadena con los detalles del vuelo.
     */
    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDateTime=" + departureDateTime +
                ", duration=" + duration +
                '}';
    }
}
