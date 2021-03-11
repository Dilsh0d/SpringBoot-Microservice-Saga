package uz.kassa.microservice.saga.common;

/**
 * @author Tadjiev Dilshod
 */
public class SagaDoesNotFindByAssociateIdException extends RuntimeException {
    public SagaDoesNotFindByAssociateIdException(String message) {
        super(message);
    }
}
