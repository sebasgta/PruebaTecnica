package co.com.prueba;


/**
 *  Clase Llamada
 */
public class LLamada {

    // Estado de la llamada
    private AtencionEnum atendida;
    // Asesor que atiende la llamada
    private String asesor;
    // Identificacion de la llamada
    private String identificacion;

    public LLamada(String identificacion){
        this.atendida = AtencionEnum.SINATENDER;
        this.identificacion = identificacion;
    }

    public AtencionEnum getAtendida() {
        return atendida;
    }

    public void setAtendida(AtencionEnum atendida) {
        this.atendida = atendida;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
