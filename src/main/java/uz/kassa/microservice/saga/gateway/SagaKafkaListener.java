package uz.kassa.microservice.saga.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import uz.kassa.microservice.saga.event.SagaEventMessage;
import uz.kassa.microservice.saga.process.SagaClassesConfigurer;

/**
 * @author Tadjiev Dilshod
 */
public class SagaKafkaListener {

    @Lazy
    @Autowired
    private SagaClassesConfigurer sagaClassConfigurer;

    @KafkaListener(topics = SagaGateway.TOPIC_SAGA, containerFactory = "concurrentMessageListenerContainer")
    protected void kafkaListener(@Payload(required = false) SagaEventMessage<?> value, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        sagaClassConfigurer.startSagaEventProcess(value);
    }
}
