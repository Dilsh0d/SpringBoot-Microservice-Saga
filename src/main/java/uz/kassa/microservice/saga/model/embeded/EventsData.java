package uz.kassa.microservice.saga.model.embeded;

import uz.kassa.microservice.saga.event.SagaEventMessage;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tadjiev Dilshod
 */
public class EventsData implements Serializable {
    private List<SagaEventMessage> eventMessages;

    public List<SagaEventMessage> getEventMessages() {
        return eventMessages;
    }

    public void setEventMessages(List<SagaEventMessage> eventMessages) {
        this.eventMessages = eventMessages;
    }
}
