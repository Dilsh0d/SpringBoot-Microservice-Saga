package uz.kassa.microservice.saga.model.domain;

/**
 * @author Tadjiev Dilshod
 */
public interface SagaState {
    String START = "Start";
    String INPROGRESS = "InProgress";
    String END = "End";
    String DEADLINE = "Deadline";
}
