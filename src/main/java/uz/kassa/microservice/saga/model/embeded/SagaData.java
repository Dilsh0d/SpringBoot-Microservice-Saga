package uz.kassa.microservice.saga.model.embeded;

import java.io.Serializable;

/**
 * @author Tadjiev Dilshod
 */
public class SagaData<T> implements Serializable {
    private T payload;

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
