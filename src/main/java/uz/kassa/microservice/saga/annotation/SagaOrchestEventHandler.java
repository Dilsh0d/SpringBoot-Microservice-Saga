package uz.kassa.microservice.saga.annotation;

import java.lang.annotation.*;

/**
 * @author Tadjiev Dilshod
 */
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SagaOrchestEventHandler {
}
