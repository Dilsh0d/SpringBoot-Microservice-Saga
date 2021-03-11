package uz.kassa.microservice.saga.test.saga2;

import uz.kassa.microservice.saga.annotation.SagaAssociateId;

/**
 * @author Tadjiev Dilshod
 */
public class Saga2EventHandler2 {
    @SagaAssociateId
    private String identifier;
    private String name;

    public Saga2EventHandler2() {
        this.identifier = identifier;
    }

    public Saga2EventHandler2(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
