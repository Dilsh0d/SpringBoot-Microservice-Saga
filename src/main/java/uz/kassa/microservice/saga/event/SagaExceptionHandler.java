package uz.kassa.microservice.saga.event;

/**
 * @author Tadjiev Dilshod
 */
public class SagaExceptionHandler {
    private String sagaId;
    private String exceptionEventClass;
    private String exceptionSagaMethodName;
    private Exception exception;

    public String getExceptionEventClass() {
        return exceptionEventClass;
    }

    public void setExceptionEventClass(String exceptionEventClass) {
        this.exceptionEventClass = exceptionEventClass;
    }

    public String getExceptionSagaMethodName() {
        return exceptionSagaMethodName;
    }

    public void setExceptionSagaMethodName(String exceptionSagaMethodName) {
        this.exceptionSagaMethodName = exceptionSagaMethodName;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setSagaId(String sagaId) {
        this.sagaId = sagaId;
    }
}
