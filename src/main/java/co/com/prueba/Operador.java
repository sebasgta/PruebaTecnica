package co.com.prueba;

/**
 *  Clase Operador
 */
public class Operador extends Empleado {

    public Operador(String identificacion,boolean libre){
        super(identificacion,libre);
        this.orden = 1;
    }
}
