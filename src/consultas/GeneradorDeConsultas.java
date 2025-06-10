package consultas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datos.Moneda;
import java.io.FileWriter;
import java.io.IOException;


public class GeneradorDeConsultas {
    public void guardarJson(Moneda conversion) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(conversion);

        String nombreArchivo = "conversion_" + conversion.getOrigen() + "_a_" + conversion.getDestino() + ".json";

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(json);
            System.out.println("conversión guardada en: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar la conversión.");
        }
    }
}
