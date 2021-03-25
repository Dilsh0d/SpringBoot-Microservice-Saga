package uz.kassa.microservice.saga.event;

import uz.kassa.microservice.saga.process.SagaEventClass;

/**
 * @author Tadjiev Dilshod
 */
public class SagaExceptionHandler {
    private String sagaId;
    private Exception exception;
    private SagaEventClass sagaEventClass;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public SagaEventClass getSagaEventClass() {
        return sagaEventClass;
    }

    public void setSagaEventClass(SagaEventClass sagaEventClass) {
        this.sagaEventClass = sagaEventClass;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }
}
