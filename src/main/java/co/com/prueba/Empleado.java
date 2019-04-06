package co.com.prueba;

/**
 *  Clase Empleado
 */
public class Empleado {

    // Orden para enumerar la herarquia
    protected int orden;
    // Estado en el que se encuentra el Empleado
    protected boolean libre;
    // Identificacion del empleado
    protected String identificacion;
    // Identificacion del cliente a atender
    protected String identificacionCliente;

    public Empleado(String identificacion,boolean libre){
        this.orden = -1;
        this.libre = true;
        this.identificacion = identificacion;
        this.identificacionCliente = "";
    }
    public int getOrden() {
        return orden;
    }

    public boolean isLibre() { return libre; }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setOcupado() {
        this.libre = false;
    }

    public void setLibre() {
        this.libre = true;
    }

    public String getIdentificacionCliente() { return identificacionCliente; }

    public void setIdentificacionCliente(String identificacionCliente) { this.identificacionCliente = identificacionCliente; }
}
