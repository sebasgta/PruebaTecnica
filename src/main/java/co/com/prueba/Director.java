package co.com.prueba;

/**
 *  Clase director
 */
public class Director extends Empleado {

    public Director(String identificacion,boolean disponible){
        super(identificacion,disponible);
        this.orden = 3;
    }
}