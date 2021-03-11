package uz.kassa.microservice.saga.process;

/**
 * @author Tadjiev Dilshod
 */
public class SagaEventClass {

    private String methodName;
    private String paramClass;
    private boolean starter = false;
    private boolean ended = false;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamClass() {
        return paramClass;
    }

    public void setParamClass(String paramClass) {
        this.paramClass = paramClass;
    }

    public boolean isStarter() {
        return starter;
    }

    public void setStarter(boolean starter) {
        this.starter = starter;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
