package uz.kassa.microservice.saga.process;

/**
 * @author Tadjiev Dilshod
 */
public class SagaExceptionMethod {
    private String methodName;
    private String paramClass;

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
}
