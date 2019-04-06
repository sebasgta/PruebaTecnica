package co.com.prueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 *  Servicio para atender las solicitudes
 */
@Service("DispatcherService")
public class Dispatcher {

    // Objeto para realizar un llamado syncronizado
    private final Object readLock = new Object();
    // Lista de empleados
    private List<Empleado> actual;

    @Autowired
    public Dispatcher(){
        this.actual = new ArrayList<>();
    }

    public void setActual(List<Empleado> actual) throws InterruptedException {
        this.actual = actual;
    }

    // Funcion de prueba
    public String callClass(){
       return "Hi";
    }

    // Metodo a invocar de forma transaccional
    @Transactional
    public LLamada dispatchCall(LLamada llamada){

        do {
            // Ejecutar syncronia
            this.changeState();

            // En caso que este la llamada en atencion
            if(llamada.getAtendida().equals(AtencionEnum.EN_ATENCION) ) {
                Optional<Empleado> empleado = this.actual.stream().filter((x) -> x.getIdentificacion().equals(llamada.getAsesor())).findFirst();
                if(empleado.isPresent()) {
                    empleado.get().setLibre();
                    empleado.get().setIdentificacionCliente("");
                    llamada.setAtendida(AtencionEnum.ATENDIDO);
                }
                else{
                    llamada.setAtendida(AtencionEnum.SINATENDER);
                }
            }

            // En caso que no se ha atendido la llamada
            if(llamada.getAtendida().equals(AtencionEnum.SINATENDER) ) {
                Optional<Empleado> operador = this.actual.stream().filter((x) -> x.getOrden() == 1 && x.isLibre()).findFirst();
                Optional<Empleado> supervisor = this.actual.stream().filter((x) -> x.getOrden() == 2 && x.isLibre()).findFirst();
                Optional<Empleado> director = this.actual.stream().filter((x) -> x.getOrden() == 3 && x.isLibre()).findFirst();

                llamada.setAsesor( operador.isPresent() ?
                    ((Operador)operador.get()).getIdentificacion() :
                    ( supervisor.isPresent() ? ((Supervisor)supervisor.get()).getIdentificacion() :
                    ( director.isPresent() ?  ((Director)director.get()).getIdentificacion() : "" )));
                Optional<Empleado> empleado = this.actual.stream().filter((x) -> x.getIdentificacion().equals(llamada.getAsesor())).findFirst();

                if( empleado.isPresent() ) {
                    empleado.get().setIdentificacionCliente(llamada.getIdentificacion());
                    empleado.get().setOcupado();
                    llamada.setAtendida( AtencionEnum.EN_ATENCION );
                }
            }

        // Espera a la llamada poder ser atendida
        }while( !llamada.getAtendida().equals(AtencionEnum.ATENDIDO) );
        return llamada;
    }

    // Metodo que emula el cambio de ocupado-libre/libre-ocupadp de cualquier empleado que no tenga un cliente en atencion
    // Se asocia como un evento que cambia la informacion con fin de tener la syncronia
    public void changeState() {
        synchronized (readLock) {
            // Cuando se desconectan
            for (int i = 0; i < this.actual.size() ; i++) {
                Empleado e = this.actual.get(i);
                if( e.isLibre()){
                    if( new Random().nextInt(8) / (i+1) == 0 ){
                        this.actual.get(i).setOcupado();
                    }
                }
                if( !e.isLibre() && e.getIdentificacionCliente().isEmpty() ){
                    if( new Random().nextInt(8) / (i+1) == 0 ){
                        this.actual.get(i).setLibre();
                    }
                }

            }
        }
    }

}
