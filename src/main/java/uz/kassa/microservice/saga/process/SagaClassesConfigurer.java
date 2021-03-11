package uz.kassa.microservice.saga.process;

import uz.kassa.microservice.saga.annotation.SagaOrchestEnd;
import uz.kassa.microservice.saga.annotation.SagaOrchestEventHandler;
import uz.kassa.microservice.saga.annotation.SagaOrchestStart;
import uz.kassa.microservice.saga.event.SagaEventMessage;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Tadjiev Dilshod
 */
public class SagaClassesConfigurer {

    private final List<SagaClass<?>> sagaConfigurations = new ArrayList<>();
    private ExecutorService sagaExecutor = Executors.newFixedThreadPool(3);

    public <T> void registerSaga(Class<T> sagaType, Consumer<SagaClass<T>> sagaConfigurer) {
        SagaClass<T> configurer = new SagaClass<>(sagaType);
        sagaConfigurer.accept(configurer);
        this.sagaConfigurations.add(configurer);

        associateSagaEvents(configurer,sagaType);
    }

    public void startSagaEventProcess(SagaEventMessage<?> eventMessage) {
        sagaConfigurations.stream()
                .filter(sagaClass -> sagaClass.isSagaEvent(eventMessage.getType()))
                .forEach(sagaClass -> sagaExecutor.execute(sagaClass.startEventHandlerProcess(eventMessage)));
    }

    private <T> void associateSagaEvents(SagaClass<T> configurer, Class<T> sagaType) {
        Method[] methods = sagaType.getDeclaredMethods();
        for (Method method : methods) {
            SagaOrchestEventHandler eventHandler = method.getDeclaredAnnotation(SagaOrchestEventHandler.class);
            if (eventHandler != null) {
                SagaEventClass sagaEventClass = new SagaEventClass();
                sagaEventClass.setMethodName(method.getName());
                sagaEventClass.setParamClass(method.getParameters()[0].getType().getName());

                SagaOrchestStart sagaOrchestStart = method.getDeclaredAnnotation(SagaOrchestStart.class);
                if (sagaOrchestStart != null) {
                    sagaEventClass.setStarter(true);
                }

                SagaOrchestEnd sagaOrchestEnd = method.getDeclaredAnnotation(SagaOrchestEnd.class);
                if (sagaOrchestEnd != null) {
                    sagaEventClass.setEnded(true);
                }

                configurer.addEvents(sagaEventClass);
            }
        }
    }

    public void destroyBean(){
        sagaExecutor.shutdown();
    }

}
