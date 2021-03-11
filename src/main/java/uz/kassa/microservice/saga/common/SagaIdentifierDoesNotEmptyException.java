package uz.kassa.microservice.saga.common;

/**
 * @author Tadjiev Dilshod
 */
public class SagaIdentifierDoesNotEmptyException extends RuntimeException{
    public SagaIdentifierDoesNotEmptyException(String message) {
        super(message);
    }
}
