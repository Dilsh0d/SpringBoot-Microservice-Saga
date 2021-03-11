package uz.kassa.microservice.saga.resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Tadjiev Dilshod
 */
public class SpringSagaResourceInjector implements SagaResourceInject, ApplicationContextAware {

    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    public void injectResources(Object saga) {
        autowireCapableBeanFactory.autowireBeanProperties(saga, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
    }
}
