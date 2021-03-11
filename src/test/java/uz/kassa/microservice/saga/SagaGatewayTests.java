package uz.kassa.microservice.saga;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.kassa.microservice.saga.gateway.SagaGateway;
import uz.kassa.microservice.saga.test.Saga3EventHandler;
import uz.kassa.microservice.saga.test.SagaEventtt;
import uz.kassa.microservice.saga.test.SagaSecondEventtt;
import uz.kassa.microservice.saga.test.SagaTest2EventHandler;

/**
 * @author Tadjiev Dilshod
 */
@SpringBootTest
public class SagaGatewayTests {
    @Autowired
    SagaGateway sagaGateway;

    @Test
    void contextLoads() {
        SagaEventtt sagaEventtt = new SagaEventtt();
        sagaEventtt.setIdentifier("122");

        SagaSecondEventtt sagaEventtt1 = new SagaSecondEventtt();
        sagaEventtt1.setName("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        sagaEventtt1.setIdentifier("122");

        SagaTest2EventHandler sagaEventtt2 = new SagaTest2EventHandler();
        sagaEventtt2.setIdentifier("122");

        Saga3EventHandler sagaEventttSaga3 = new Saga3EventHandler();
        sagaEventttSaga3.setIdentifier("122");

        sagaGateway.send(sagaEventtt);
        sagaGateway.send(sagaEventtt2);
        sagaGateway.send(sagaEventttSaga3);
        sagaGateway.send(sagaEventtt1);
    }

    @Test
    void contextLoads22() {
        SagaSecondEventtt sagaEventtt = new SagaSecondEventtt();
        sagaEventtt.setIdentifier("122");
        sagaGateway.send(sagaEventtt);
    }
}
