package interaccion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import consultas.ConsultaAAPI;
import consultas.GeneradorDeConsultas;
import datos.Moneda;
import datos.DatosMoneda;

public class InteraccionUsuario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsultaAAPI consulta = new ConsultaAAPI();
        GeneradorDeConsultas generador = new GeneradorDeConsultas();

        while (true) {
            System.out.println("\n***** Conversor de Monedas *****");
            System.out.print("Ingresa la moneda de origen (Ejemplo: USD, EUR, CLP, JPY) o 'salir' para terminar: ");
            String monedaOrigen = scanner.next().toUpperCase();

            if (monedaOrigen.equals("SALIR")) {
                System.out.println("Gracias por usar el conversor. ¡Hasta pronto!");
                break;
            }

            System.out.print("Ingresa la cantidad en " + monedaOrigen + ": ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine();  // Limpiar buffer de entrada

            System.out.print("Ingresa varias monedas de destino separadas por comas (Ejemplo: USD, EUR, CLP, JPY): ");
            String monedasEntrantes = scanner.nextLine().toUpperCase();
            List<String> monedasDestino = Arrays.asList(monedasEntrantes.split(","));

            // Obtener tasas de conversión desde la API
            JsonObject conversionRates = consulta.obtenerTasa(monedaOrigen);
            if (conversionRates == null) {
                System.out.println("Error al obtener tasas de la API.");
                continue;
            }

            for (String monedaDestino : monedasDestino) {
                monedaDestino = monedaDestino.trim(); //

                double tasaDestino = DatosMoneda.obtenerTasaDesdeJson(conversionRates, monedaDestino);
                if (tasaDestino == 0.0) {
                    System.out.println(" Moneda destino no encontrada en la API: " + monedaDestino);
                    continue;
                }


                double resultadoConversion = cantidad * tasaDestino;
                Moneda resultado = new Moneda(monedaOrigen, monedaDestino, cantidad, resultadoConversion);


                LocalDateTime fechaHora = LocalDateTime.now();
                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                resultado.setFecha(fechaHora.format(formatoFecha));

                // para que el resultado se muestre en consola
                Gson gson = new Gson();
                String jsonResultado = gson.toJson(resultado);
                System.out.println("\n Conversión en formato JSON:");
                System.out.println(jsonResultado);


                generador.guardarJson(resultado);
            }
        }

        scanner.close();
    }
}
