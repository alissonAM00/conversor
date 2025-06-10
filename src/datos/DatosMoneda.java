package datos;

import com.google.gson.JsonObject;

public class DatosMoneda {
    public static double obtenerTasaDesdeJson(JsonObject conversionRates, String monedaDestino) {
        if (conversionRates == null || !conversionRates.has(monedaDestino)) {
            System.out.println("Error: No se encontró la tasa de conversión para " + monedaDestino);
            return 0.0;
        }
        return conversionRates.get(monedaDestino).getAsDouble();
    }
}