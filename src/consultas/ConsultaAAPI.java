package consultas;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ConsultaAAPI {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/65829290a11151c421f0e299/latest/";

    // ✅ Método para obtener todas las tasas de conversión desde la API
    public JsonObject obtenerTasa(String monedaBase) {
        String url = API_URL + monedaBase;
        URI direccion = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
            return jsonResponse.getAsJsonObject("conversion_rates"); //
        } catch (IOException | InterruptedException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    // Método para obtener el valor de una moneda específica
    public double obtenerValorMoneda(String monedaBase, String monedaDestino) {
        JsonObject tasas = obtenerTasa(monedaBase);
        if (tasas == null || !tasas.has(monedaDestino)) {
            System.out.println(" Error: No se encontró la tasa de conversión para " + monedaDestino);
            return 0.0;
        }
        return tasas.get(monedaDestino).getAsDouble();
    }
}





