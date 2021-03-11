package uz.kassa.microservice.saga.common;

/**
 * @author Tadjiev Dilshod
 */
public class SagaIdentifierNullException extends RuntimeException{
    public SagaIdentifierNullException(String message) {
        super(message);
    }
}
