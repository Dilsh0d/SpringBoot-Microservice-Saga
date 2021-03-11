package uz.kassa.microservice.saga.test.saga2;

import uz.kassa.microservice.saga.annotation.SagaAssociateId;

/**
 * @author Tadjiev Dilshod
 */
public class Saga2EventHandler3 {

    @SagaAssociateId
    private String identifier;

    public Saga2EventHandler3() {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
