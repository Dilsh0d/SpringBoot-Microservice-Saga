package uz.kassa.microservice.saga;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.kassa.microservice.saga.gateway.SagaGateway;
import uz.kassa.microservice.saga.test.saga1.Saga1EventHandler;
import uz.kassa.microservice.saga.test.saga2.Saga2EventHandler1;
import uz.kassa.microservice.saga.test.saga2.Saga2EventHandler2;
import uz.kassa.microservice.saga.test.saga2.Saga2EventHandler3;

/**
 * @author Tadjiev Dilshod
 */
@SpringBootTest
public class SagaGatewayTests {
    @Autowired
    SagaGateway sagaGateway;

    @Test
    void contextLoads() {
        Saga2EventHandler1 saga2EventHandler1 = new Saga2EventHandler1();
        saga2EventHandler1.setIdentifier("122");

        Saga2EventHandler2 sagaEventtt1 = new Saga2EventHandler2();
        sagaEventtt1.setName("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        sagaEventtt1.setIdentifier("122");

        Saga2EventHandler3 sagaEventtt2 = new Saga2EventHandler3();
        sagaEventtt2.setIdentifier("122");

        Saga1EventHandler sagaEventttSaga3 = new Saga1EventHandler();
        sagaEventttSaga3.setIdentifier("122");

        sagaGateway.send(saga2EventHandler1);
        sagaGateway.send(sagaEventtt2);
        sagaGateway.send(sagaEventttSaga3);
        sagaGateway.send(sagaEventtt1);
    }

    @Test
    void contextLoads22() {
        Saga2EventHandler2 sagaEventtt = new Saga2EventHandler2();
        sagaEventtt.setIdentifier("122");
        sagaGateway.send(sagaEventtt);
    }
}
