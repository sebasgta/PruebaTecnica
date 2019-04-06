package co.com.prueba;

/**
 *  Clase Supervisor
 */
public class Supervisor extends Empleado {

    public Supervisor(String identificacion,boolean libre){
        super(identificacion,libre);
        this.orden = 2;
    }
}