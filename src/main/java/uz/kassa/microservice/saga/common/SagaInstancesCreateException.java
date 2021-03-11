package uz.kassa.microservice.saga.common;

/**
 * @author Tadjiev Dilshod
 */
public class SagaInstancesCreateException extends RuntimeException{
    public SagaInstancesCreateException(String message) {
        super(message);
    }
}
