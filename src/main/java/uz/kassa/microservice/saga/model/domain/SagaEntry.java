package uz.kassa.microservice.saga.model.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import uz.kassa.microservice.saga.model.embeded.EventsData;
import uz.kassa.microservice.saga.model.embeded.SagaData;

/**
 * @author Tadjiev Dilshod
 */
@RedisHash("saga_entry")
public class SagaEntry {
    @Id
    private String id;
    private String sagaType;

    private EventsData events;
    private SagaData payload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSagaType() {
        return sagaType;
    }

    public void setSagaType(String sagaType) {
        this.sagaType = sagaType;
    }

    public EventsData getEvents() {
        return events;
    }

    public void setEvents(EventsData events) {
        this.events = events;
    }

    public SagaData getPayload() {
        return payload;
    }

    public void setPayload(SagaData payload) {
        this.payload = payload;
    }
}
