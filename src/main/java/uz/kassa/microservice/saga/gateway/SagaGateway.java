package uz.kassa.microservice.saga.gateway;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ProducerFactory;
import uz.kassa.microservice.saga.annotation.SagaAssociateId;
import uz.kassa.microservice.saga.common.SagaIdentifierDoesNotEmptyException;
import uz.kassa.microservice.saga.common.SagaIdentifierNullException;
import uz.kassa.microservice.saga.event.SagaEventMessage;

import java.lang.reflect.Field;

/**
 * @author Tadjiev Dilshod
 */
public class SagaGateway {

    public static final String TOPIC_SAGA = "topic_saga";

    @Autowired
    private ProducerFactory producerFactory;

    public <T> void send(T event) {
        String identifier = getIdentifier(event);

        SagaEventMessage<T> eventMessage = new SagaEventMessage<T>();
        eventMessage.setId(identifier);
        eventMessage.setType(event.getClass().getName());
        eventMessage.setPayload(event);


        Producer producer = producerFactory.createProducer();
        try {
            producer.beginTransaction();
            producer.send(new ProducerRecord(TOPIC_SAGA, identifier, eventMessage));
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
            producer.abortTransaction();
            e.printStackTrace();
        }
    }

    private <T> String getIdentifier(T event) {
        Field fs[] = event.getClass().getDeclaredFields();
        String identifier = "";
        for (Field f : fs) {
            SagaAssociateId sagaAssociateId = f.getAnnotation(SagaAssociateId.class);
            if (sagaAssociateId != null) {
                f.setAccessible(true);
                try {
                    identifier = (String) f.get(event);
                } catch (IllegalAccessException e) {
                    throw new SagaIdentifierNullException("Does find in the class " + event.getClass().getName() + " @SagaIdentifierId annotation");
                }
                break;
            }
        }
        if(identifier==null || "".equals(identifier.trim())){
            throw new SagaIdentifierDoesNotEmptyException("@SagaIdentifierId annotation field is empty or does not use it is annotation in the class " + event.getClass().getName() + "!");
        }
        return identifier;
    }
}
