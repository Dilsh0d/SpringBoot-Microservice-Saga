package uz.kassa.microservice.saga.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Tadjiev Dilshod
 */
public class SagaEventMessage<T> implements Serializable {
    private String id;
    private String type;

    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS,include = JsonTypeInfo.As.PROPERTY, property = "@modelClass")
    private T payload;

    public SagaEventMessage(String id, String type, T payload) {
        this.id = id;
        this.type = type;
        this.payload = payload;
    }

    public SagaEventMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SagaEventMessage<?> that = (SagaEventMessage<?>) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
