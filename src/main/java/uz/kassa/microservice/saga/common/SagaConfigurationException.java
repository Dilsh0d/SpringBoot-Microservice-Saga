package uz.kassa.microservice.saga.common;

/**
 * @author Tadjiev Dilshod
 */
public class SagaConfigurationException extends RuntimeException {
    public SagaConfigurationException(String message) {
        super(message);
    }
}
