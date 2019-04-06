package co.com.prueba;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Dispatcher.class)
public class MainTest {


    @MockBean
    public Dispatcher dispatch;

    @MockBean
    private DispatcherController dispatchInject;

    // Cargar los objetos para ejecucion de la prueba
     @Before
     public void init(){
         this.dispatch = new Dispatcher();
         dispatchInject = new DispatcherController(dispatch);
     }

    @Test
    public void testExecute() throws InterruptedException {

        // Creacion de los empleados
        List<Empleado> listado= new ArrayList<>();
        listado.add( new Director("Boss",false) );
        for (int i = 0; i < 1 ; i++) {
            listado.add( new Operador("Empleado_" + i,i%2 ==0? true: false) );
        }
        for (int i = 0; i < 1 ; i++) {
            listado.add( new Supervisor("Supervisor" + i,i%3 ==0? true: false) );
        }
        this.dispatch.setActual(listado);


        // Creacion de las llamadas
        List<LLamada> llamadas = new ArrayList<>();
        for (int i = 0; i < 200 ; i++) {
            llamadas.add( new LLamada("LLAMADA_"+i) );
        }

        // Iterar paralelamente agrupando por el tipo de identificacion que es unico y enviando todos los llamados al metodo
        llamadas.stream().parallel().collect(Collectors.groupingBy( x -> x.getIdentificacion() )).forEach( (type,list) -> dispatchInject.dispatch(list.get(0)) );

        // Verificar que en efecto las llamadas son atendidas
        for ( LLamada l : llamadas ) {
            assert( !l.getAsesor().isEmpty() );
        }
    }

    @Test
    public void testCall() {
        System.out.println(this.dispatch.callClass());
    }
}