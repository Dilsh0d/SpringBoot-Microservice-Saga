package uz.kassa.microservice.saga;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.kassa.microservice.saga.event.SagaEventMessage;
import uz.kassa.microservice.saga.model.domain.SagaEntry;
import uz.kassa.microservice.saga.model.embeded.EventsData;
import uz.kassa.microservice.saga.model.embeded.SagaData;
import uz.kassa.microservice.saga.model.repository.SagaRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Tadjiev Dilshod
 */
@SpringBootTest
public class SagaRedisApplicationTests {

    @Autowired
    SagaRepository sagaRepository;

    @Test
    void contextLoads() {

        SagaEventMessage eventMessage = new SagaEventMessage();
        eventMessage.setId(UUID.randomUUID().toString());
        eventMessage.setPayload(null);
        eventMessage.setType(String.class.getTypeName());

        SagaEventMessage eventMessage1 = new SagaEventMessage();
        eventMessage1.setId(UUID.randomUUID().toString());
        eventMessage1.setPayload(null);
        eventMessage1.setType(String.class.getTypeName());

        SagaEventMessage eventMessage2 = new SagaEventMessage();
        eventMessage2.setId(UUID.randomUUID().toString());
        eventMessage2.setPayload(null);
        eventMessage2.setType(String.class.getTypeName());

        SagaData sagaData = new SagaData();
//        sagaData.setType(BigDecimal.class.getTypeName());
        sagaData.setPayload(eventMessage2);

        EventsData eventsData = new EventsData();
        eventsData.setEventMessages(new ArrayList<>());
        eventsData.getEventMessages().add(eventMessage);
        eventsData.getEventMessages().add(eventMessage1);



        String id = UUID.randomUUID().toString();

        SagaEntry sagaEntry = new SagaEntry();
        sagaEntry.setId(id);
        sagaEntry.setSagaType(Integer.class.getTypeName());
        sagaEntry.setEvents(eventsData);
        sagaEntry.setPayload(sagaData);

        sagaRepository.save(sagaEntry);

        Optional<SagaEntry> sagaEntry1 = sagaRepository.findById(id);
        if(sagaEntry1.isPresent()){
            System.out.println();
        }
    }
}
