package co.com.prueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *  Controlador para atender las solicitudes que llegan a la clase Dispatcher
 */
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class DispatcherController {

    private final Dispatcher dispatcher;

    @Autowired
    public DispatcherController(@Qualifier("DispatcherService") Dispatcher dispatcher ){
        this.dispatcher = dispatcher;
    }

    @Transactional
    public LLamada dispatch(LLamada llamada){
       return this.dispatcher.dispatchCall(llamada);
    }

    public String callClass(){
        return this.dispatcher.callClass();
    }
}
