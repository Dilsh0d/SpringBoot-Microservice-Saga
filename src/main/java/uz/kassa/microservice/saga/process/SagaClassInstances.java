package uz.kassa.microservice.saga.process;

import uz.kassa.microservice.saga.event.SagaEventMessage;
import uz.kassa.microservice.saga.model.repository.SagaRepository;
import uz.kassa.microservice.saga.resource.SagaResourceInject;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Tadjiev Dilshod
 */
public interface SagaClassInstances<S> {
    Class<S> type();

    SagaEventClass getEventClass(String eventClass);

    SagaEventMessage getSagaEventMessage() throws InterruptedException;

    SagaRepository repository();

    SagaResourceInject resourceInject();

    ReentrantLock reentrantLock();

    void clear();

    void eventHandlerProcess(SagaEventMessage<S> eventMessage);
}
