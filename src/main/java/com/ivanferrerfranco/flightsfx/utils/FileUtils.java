package com.ivanferrerfranco.flightsfx.utils;

import com.ivanferrerfranco.flightsfx.model.Flight;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad para manejar la lectura y escritura de vuelos en un archivo.
 */
public class FileUtils {

    private static final String FILE_PATH = "flights.txt"; // Ruta del archivo donde se almacenan los datos de los vuelos.

    /**
     * Método para cargar vuelos desde un archivo de texto.
     *
     * @return Una lista de objetos Flight cargados desde el archivo.
     */
    public static List<Flight> loadFlights() {
        List<Flight> flights = new ArrayList<>(); // Lista para almacenar los vuelos.

        try {
            // Leer todas las líneas del archivo y filtrar las líneas vacías.
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH))
                    .stream()
                    .skip(1) // Omitir la cabecera del archivo (la primera línea).
                    .filter(line -> !line.isBlank()) // Ignorar líneas vacías.
                    .toList();

            // Procesar cada línea del archivo.
            for (String line : lines) {
                String[] parts = line.split(";"); // Dividir la línea en partes usando el separador ";".

                // Verificar que la línea tenga exactamente 4 campos.
                if (parts.length != 4) {
                    System.out.println("Línea con formato incorrecto: " + line);
                    continue; // Saltar esta línea y continuar con las demás.
                }

                // Extraer los datos del vuelo.
                String flightNumber = parts[0]; // Número del vuelo.
                String destination = parts[1]; // Destino del vuelo.
                LocalDateTime departureDateTime = LocalDateTime.parse(parts[2], DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")); // Fecha y hora de salida.
                LocalTime duration = LocalTime.parse(parts[3]); // Duración del vuelo.

                // Crear un objeto Flight y agregarlo a la lista.
                flights.add(new Flight(flightNumber, destination, departureDateTime, duration));
            }
        } catch (IOException e) {
            // Capturar y mostrar cualquier error al leer el archivo.
            System.out.println("Error al cargar los vuelos: " + e.getMessage());
        }

        return flights; // Devolver la lista de vuelos cargados.
    }

    /**
     * Método para guardar una lista de vuelos en un archivo de texto.
     *
     * @param flights Lista de objetos Flight a guardar en el archivo.
     */
    public static void saveFlights(List<Flight> flights) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Escribir la cabecera del archivo.
            writer.println("FlightNumber;Destination;DepartureDateTime;Duration");

            // Escribir cada vuelo como una línea en el archivo.
            for (Flight flight : flights) {
                writer.println(flight.getFlightNumber() + ";" +
                        flight.getDestination() + ";" +
                        flight.getDepartureDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")) + ";" +
                        flight.getDuration());
            }
        } catch (IOException e) {
            // Capturar y mostrar cualquier error al escribir en el archivo.
            System.out.println("Error al guardar los vuelos: " + e.getMessage());
        }
    }

}
