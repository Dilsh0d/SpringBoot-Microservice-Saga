package uz.kassa.microservice.saga.process;

import uz.kassa.microservice.saga.common.SagaDoesNotFindByAssociateIdException;
import uz.kassa.microservice.saga.common.SagaInstancesCreateException;
import uz.kassa.microservice.saga.event.SagaEventMessage;
import uz.kassa.microservice.saga.model.domain.SagaEntry;
import uz.kassa.microservice.saga.model.embeded.EventsData;
import uz.kassa.microservice.saga.model.embeded.SagaData;
import uz.kassa.microservice.saga.model.repository.SagaRepository;
import uz.kassa.microservice.saga.resource.SagaResourceInject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * @author Tadjiev Dilshod
 */
public class SagaClass<T> {

    private final Class<T> type;
    private Supplier<SagaRepository> sagaStoreSupplier;
    private Supplier<SagaResourceInject> sagaResourceInjectorSupplier;
    private Map<String,SagaEventClass> sagaClassEventMap = new HashMap<>();

    private ConcurrentMap<String,BlockingQueue<SagaEventMessage<?>>> eventsBlockedQueueMap = new ConcurrentHashMap<>();
    private ConcurrentMap<String,ReentrantLock> associateSagaMap = new ConcurrentHashMap<>();

    public SagaClass(Class<T> type) {
        this.type = type;
    }

    public void setSagaRepository(Supplier<SagaRepository> sagaStoreSupplier,Supplier<SagaResourceInject> sagaResourceInjectorSupplier) {
        this.sagaStoreSupplier = sagaStoreSupplier;
        this.sagaResourceInjectorSupplier = sagaResourceInjectorSupplier;
    }

    public void addEvents(SagaEventClass eventClass) {
        sagaClassEventMap.put(eventClass.getParamClass(),eventClass);
    }

    public boolean isSagaEvent(String _clazz){
        return sagaClassEventMap.containsKey(_clazz);
    }

    public Runnable startEventHandlerProcess(SagaEventMessage<?> eventMessage) {
        if (!eventsBlockedQueueMap.containsKey(eventMessage.getId())) {
            eventsBlockedQueueMap.put(eventMessage.getId(), new LinkedBlockingQueue<>());
        }
        try {
            eventsBlockedQueueMap.get(eventMessage.getId()).put(eventMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new SagaClassInstancesImpl<>(this, eventMessage.getId());
    }

    public static class SagaClassInstancesImpl<S> implements SagaClassInstances,Runnable {

        private SagaClass<S> configurer;
        private Supplier<S> sagaFactory = () -> newInstance(configurer.type);
        private SagaEntry sagaEntry;
        private String sagaId;

        private static <S> S newInstance(Class<S> type) {
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new SagaInstancesCreateException("Exception while trying to instantiate a new Saga");
            }
        }

        private SagaClassInstancesImpl(SagaClass<S> configurer, String sagaId) {
            this.configurer = configurer;
            this.sagaId = sagaId;
        }

        @Override
        public Class type() {
            return configurer.type;
        }

        @Override
        public SagaEventClass getEventClass(String eventClass) {
            return configurer.sagaClassEventMap.get(eventClass);
        }

        @Override
        public SagaEventMessage getSagaEventMessage() throws InterruptedException {
            return configurer.eventsBlockedQueueMap.get(sagaId).take();
        }

        @Override
        public SagaRepository repository() {
            return configurer.sagaStoreSupplier.get();
        }

        @Override
        public SagaResourceInject resourceInject() {
            return configurer.sagaResourceInjectorSupplier.get();
        }

        @Override
        public ReentrantLock reentrantLock() {
            if(!configurer.associateSagaMap.containsKey(sagaId)){
                configurer.associateSagaMap.put(sagaId,new ReentrantLock());
            }
            return configurer.associateSagaMap.get(sagaId);
        }

        @Override
        public void clear() {
            if (configurer.associateSagaMap.containsKey(sagaId)
                    && configurer.eventsBlockedQueueMap.get(sagaId).size() == 0) {
                configurer.associateSagaMap.remove(sagaId);
            }
            if(configurer.eventsBlockedQueueMap.containsKey(sagaId) && configurer.eventsBlockedQueueMap.get(sagaId).size()==0){
                configurer.eventsBlockedQueueMap.remove(sagaId);
            }
        }

        @Override
        public void eventHandlerProcess(SagaEventMessage eventMessage) {
            try {
                SagaEventClass sagaEventClass = getEventClass(eventMessage.getType());
                S sagaRoot = sagaResourcesInject(eventMessage, sagaEventClass);
                invokeSagaMethod(sagaRoot, sagaEventClass, eventMessage);
                saveSagaState(eventMessage, sagaEventClass, sagaRoot);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }

        }


        private void invokeSagaMethod(S sagaRoot, SagaEventClass sagaEventClass, SagaEventMessage eventMessage) {
            try {
                Method method = sagaRoot.getClass().getDeclaredMethod(sagaEventClass.getMethodName(), eventMessage.getPayload().getClass());
                method.invoke(sagaRoot, eventMessage.getPayload());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        private S sagaResourcesInject(SagaEventMessage eventMessage, SagaEventClass sagaEventClass) {
            S sagaRoot = null;
            if (sagaEventClass.isStarter()) {
                sagaRoot = sagaFactory.get();
            } else {
                Optional<SagaEntry> sagaEntryOptional = repository().findById(eventMessage.getId());
                if (sagaEntryOptional.isPresent()) {
                    this.sagaEntry = sagaEntryOptional.get();
                    sagaRoot = (S) sagaEntryOptional.get().getPayload().getPayload();
                } else {
                    throw new SagaDoesNotFindByAssociateIdException("Saga Instance does not find by Id=" + eventMessage.getId()
                            + " from redis storage method=["+sagaEventClass.getMethodName() +"] argClass=["+sagaEventClass.getParamClass()+"]");
                }
            }
            resourceInject().injectResources(sagaRoot);

            return sagaRoot;
        }

        private void saveSagaState(SagaEventMessage eventMessage, SagaEventClass sagaEventClass, S sagaRoot) {
            if (sagaEventClass.isStarter()) {
                sagaEntry = new SagaEntry();
                sagaEntry.setId(eventMessage.getId());
                sagaEntry.setSagaType(sagaRoot.getClass().getName());
                sagaEntry.setEvents(new EventsData());
                sagaEntry.getEvents().setEventMessages(new ArrayList<>());
                sagaEntry.setPayload(new SagaData());
            }
            sagaEntry.getPayload().setPayload(sagaRoot);
            sagaEntry.getEvents().getEventMessages().add(eventMessage);

            if (sagaEventClass.isEnded()) {
                repository().deleteById(eventMessage.getId());
            } else {
                repository().save(sagaEntry);
            }
        }

        @Override
        public void run() {
            try {
                reentrantLock().lock();
                eventHandlerProcess(getSagaEventMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock().unlock();
                clear();
            }
        }
    }
}
