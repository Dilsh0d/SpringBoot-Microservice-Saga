package uz.kassa.microservice.saga.test;

import org.springframework.beans.factory.annotation.Autowired;
import uz.kassa.microservice.saga.annotation.SagaOrchestEnd;
import uz.kassa.microservice.saga.annotation.SagaOrchestEventHandler;
import uz.kassa.microservice.saga.annotation.SagaOrchestStart;
import uz.kassa.microservice.saga.annotation.SagaOrchestration;
import uz.kassa.microservice.saga.gateway.SagaGateway;
import uz.kassa.microservice.saga.model.repository.SagaRepository;
import uz.kassa.microservice.saga.test.saga2.Saga2EventHandler1;
import uz.kassa.microservice.saga.test.saga2.Saga2EventHandler2;
import uz.kassa.microservice.saga.test.saga2.Saga2EventHandler3;

/**
 * @author Tadjiev Dilshod
 */
@SagaOrchestration
public class SagaTest2Bean {
    private String identifierId;
    private boolean workFirstEvent = false;
    private boolean workSecondEvent = false;

    @Autowired
    private transient SagaRepository sagaRepository;

    @Autowired
    private transient SagaGateway sagaGateway;

    @SagaOrchestStart
    @SagaOrchestEventHandler
    public void handler(Saga2EventHandler1 eventtt) {
        this.identifierId = eventtt.getIdentifier();
        sagaGateway.send(new Saga2EventHandler2(identifierId,"UzKasasaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        this.workFirstEvent = true;

        System.out.println("Hello");
    }

    @SagaOrchestEnd
    @SagaOrchestEventHandler
    public void handler(Saga2EventHandler2 eventtt) {
        System.out.println(eventtt.getName());
        System.out.println(eventtt.getName());
        System.out.println(eventtt.getName());
        System.out.println(eventtt.getName());
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        this.workFirstEvent = true;
    }

    @SagaOrchestEventHandler
    public void handler(Saga2EventHandler3 eventtt) {
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        this.workFirstEvent = true;
    }

    private void deadline() {
        if (workFirstEvent && workSecondEvent) {
            // @TODO saga end
        } else {
            // @TODO rollback
        }
    }

    public String getIdentifierId() {
        return identifierId;
    }

    public void setIdentifierId(String identifierId) {
        this.identifierId = identifierId;
    }

    public boolean isWorkFirstEvent() {
        return workFirstEvent;
    }

    public void setWorkFirstEvent(boolean workFirstEvent) {
        this.workFirstEvent = workFirstEvent;
    }

    public boolean isWorkSecondEvent() {
        return workSecondEvent;
    }

    public void setWorkSecondEvent(boolean workSecondEvent) {
        this.workSecondEvent = workSecondEvent;
    }
}
