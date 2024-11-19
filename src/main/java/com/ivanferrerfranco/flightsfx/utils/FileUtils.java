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
 * Clase de utilidad para manejar la lectura y escritura de datos de vuelos en un archivo.
 * Proporciona métodos para cargar vuelos desde un archivo y guardar una lista de vuelos en un archivo.
 */
public class FileUtils {

    /** Ruta del archivo donde se almacenan los datos de los vuelos. */
    private static final String FILE_PATH = "flights.txt";

    /**
     * Constructor de la clase FileUtils.
     * Este constructor es utilizado para manejar la lectura y escritura de archivos relacionados con los vuelos.
     * En esta clase no se requiere un constructor explícito, por lo que se utiliza el constructor predeterminado.
     */
    public FileUtils() {
        // El constructor no realiza ninguna acción adicional
    }

    /**
     * Método para cargar una lista de vuelos desde un archivo de texto.
     * Cada línea del archivo debe estar en el formato: `FlightNumber;Destination;DepartureDateTime;Duration`.
     *
     * @return Una lista de objetos {@link Flight} cargados desde el archivo. Si el archivo no existe
     *         o no se puede leer, se devuelve una lista vacía.
     */
    public static List<Flight> loadFlights() {
        // Lista para almacenar los vuelos cargados desde el archivo.
        List<Flight> flights = new ArrayList<>();

        try {
            // Leer todas las líneas del archivo y omitir las líneas vacías o incorrectas.
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH))
                    .stream()
                    .skip(1) // Omitir la cabecera del archivo (la primera línea).
                    .filter(line -> !line.isBlank()) // Ignorar líneas vacías.
                    .toList();

            // Procesar cada línea para crear objetos Flight.
            for (String line : lines) {
                String[] parts = line.split(";"); // Dividir la línea en partes usando el separador ";".

                // Validar que la línea tenga el número correcto de campos.
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
            // Capturar y mostrar cualquier error ocurrido al leer el archivo.
            System.out.println("Error al cargar los vuelos: " + e.getMessage());
        }

        // Devolver la lista de vuelos cargados.
        return flights;
    }

    /**
     * Método para guardar una lista de vuelos en un archivo de texto.
     * Cada vuelo se guarda como una línea en el archivo en el formato: `FlightNumber;Destination;DepartureDateTime;Duration`.
     *
     * @param flights Lista de objetos {@link Flight} que se guardarán en el archivo.
     */
    public static void saveFlights(List<Flight> flights) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Escribir la cabecera del archivo para describir los campos.
            writer.println("FlightNumber;Destination;DepartureDateTime;Duration");

            // Escribir cada vuelo como una línea en el archivo.
            for (Flight flight : flights) {
                writer.println(flight.getFlightNumber() + ";" +
                        flight.getDestination() + ";" +
                        flight.getDepartureDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")) + ";" +
                        flight.getDuration());
            }
        } catch (IOException e) {
            // Capturar y mostrar cualquier error ocurrido al guardar el archivo.
            System.out.println("Error al guardar los vuelos: " + e.getMessage());
        }
    }
}
