package uz.kassa.microservice.saga.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import uz.kassa.microservice.saga.annotation.SagaOrchestration;
import uz.kassa.microservice.saga.model.repository.SagaRepository;
import uz.kassa.microservice.saga.process.SagaClassesConfigurer;
import uz.kassa.microservice.saga.resource.SagaResourceInject;
import uz.kassa.microservice.saga.resource.SpringSagaResourceInjector;

/**
 * @author Tadjiev Dilshod
 */

public class SpringSagaAutoConfigurer implements ImportBeanDefinitionRegistrar, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        SagaClassesConfigurer sagaClassConfigurer = new SagaClassesConfigurer();

        GenericBeanDefinition sagaGenericBeanDefinition = new GenericBeanDefinition();
        sagaGenericBeanDefinition.setBeanClass(SagaClassesConfigurer.class);
        sagaGenericBeanDefinition.setInstanceSupplier(() -> sagaClassConfigurer);
        sagaGenericBeanDefinition.setDestroyMethodName("destroyBean");
        registry.registerBeanDefinition("sagaClassConfigurer", sagaGenericBeanDefinition);

        GenericBeanDefinition sagaResourceInjectorBeanDefinition = new GenericBeanDefinition();
        sagaResourceInjectorBeanDefinition.setBeanClass(SpringSagaResourceInjector.class);
        registry.registerBeanDefinition("sagaResourceInjectorBeanDefinition", sagaResourceInjectorBeanDefinition);

        registerSagaBeanDefinitions(sagaClassConfigurer);

    }

    @SuppressWarnings("unchecked")
    private void registerSagaBeanDefinitions(SagaClassesConfigurer configurer) {
        String[] sagas = beanFactory.getBeanNamesForAnnotation(SagaOrchestration.class);
        for (String saga : sagas) {
            Class<?> sagaType = beanFactory.getType(saga);
            configurer.registerSaga(sagaType, sagaClass ->  {
                sagaClass.setSagaRepository(() -> beanFactory.getBean(SagaRepository.class),
                        () -> beanFactory.getBean(SagaResourceInject.class));
            });
        }
    }
}
