package datos;

import com.google.gson.Gson;

public class Moneda {
    private String origen;
    private String destino;
    private double cantidad;
    private double resultado;
    private String fecha;

    public Moneda(String origen, String destino, double cantidad, double resultado) {
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
        this.resultado = resultado;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public double getCantidad() { return cantidad; }
    public double getResultado() { return resultado; }
    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
