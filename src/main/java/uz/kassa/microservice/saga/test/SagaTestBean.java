package uz.kassa.microservice.saga.test;

import uz.kassa.microservice.saga.annotation.SagaOrchestEventHandler;
import uz.kassa.microservice.saga.annotation.SagaOrchestStart;
import uz.kassa.microservice.saga.annotation.SagaOrchestration;
import uz.kassa.microservice.saga.test.saga1.Saga1EventHandler;

/**
 * @author Tadjiev Dilshod
 */
@SagaOrchestration
public class SagaTestBean {

    private String identifierId;

    @SagaOrchestStart
    @SagaOrchestEventHandler
    public void handler(Saga1EventHandler eventtt) {
        this.identifierId = eventtt.getIdentifier();
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");
        System.out.println("Saga 3 Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3Saga 3");

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
}
