package consultas;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Scanner;

public class ConversorDeMonedas {
    public static void main(String[] args) {
        String jsonString = "{ \"conversion_rates\": { \"USD\": 1, \"CLP\": 931.72, \"EUR\": 0.87 } }";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa la moneda de origen (USD, CLP, EUR): ");
        String monedaOrigen = scanner.next().toUpperCase();

        System.out.print("Ingresa la cantidad en " + monedaOrigen + ": ");
        double cantidad = scanner.nextDouble();

        System.out.print("Ingresa la moneda destino (USD, CLP, EUR): ");
        String monedaDestino = scanner.next().toUpperCase();

        // 🏗 Calcular conversión
        double resultado = convertir(jsonString, monedaOrigen, monedaDestino, cantidad);


        if (resultado > 0) {
            System.out.println(cantidad + " " + monedaOrigen + " = " + resultado + " " + monedaDestino);
        } else {
            System.out.println("Moneda no encontrada.");
        }

        scanner.close();
    }

    public static double convertir(String jsonString, String monedaOrigen, String monedaDestino, double cantidad) {
        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject tasas = json.getAsJsonObject("conversion_rates");

        if (!tasas.has(monedaOrigen) || !tasas.has(monedaDestino)) return 0.0;

        // Calcular conversión usando relación entre tasas
        double tasaOrigen = tasas.get(monedaOrigen).getAsDouble();
        double tasaDestino = tasas.get(monedaDestino).getAsDouble();
        return cantidad * (tasaDestino / tasaOrigen);
    }
}