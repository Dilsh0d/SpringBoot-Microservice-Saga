package uz.kassa.microservice.saga.annotation;

import org.springframework.context.annotation.Import;
import uz.kassa.microservice.saga.config.EnableSagaOrchestrationConfig;

import java.lang.annotation.*;

/**
 * @author Tadjiev Dilshod
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EnableSagaOrchestrationConfig.class})
public @interface EnableSagaOrchestration {
}
