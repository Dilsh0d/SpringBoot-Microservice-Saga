package uz.kassa.microservice.saga.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import uz.kassa.microservice.saga.event.SagaEventMessage;
import uz.kassa.microservice.saga.gateway.SagaGateway;
import uz.kassa.microservice.saga.gateway.SagaKafkaListener;

/**
 * @author Tadjiev Dilshod
 */
@Configuration
@ConditionalOnClass({KafkaAutoConfiguration.class})
@Import(SpringSagaAutoConfigurer.class)
@EnableConfigurationProperties(SagaConfigProperties.class)
@AutoConfigureAfter(name = {
        "uz.kassa.microservice.saga.config.RedisConfig"
})
@EnableRedisRepositories(value = "uz.kassa.microservice.saga.model.repository")
public class EnableSagaOrchestrationConfig {

    @Bean
    @ConditionalOnClass(ConsumerFactory.class)
    public ConcurrentKafkaListenerContainerFactory<String, SagaEventMessage> concurrentMessageListenerContainer(ConsumerFactory<String, SagaEventMessage> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, SagaEventMessage> listenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<String, SagaEventMessage>();
        listenerContainerFactory.setConsumerFactory(consumerFactory);
        listenerContainerFactory.setConcurrency(5);
        listenerContainerFactory.setMessageConverter(new JsonMessageConverter());
        return listenerContainerFactory;
    }

    @Bean
    @ConditionalOnClass(KafkaTemplate.class)
    public SagaGateway createSagaGateway(){
        return new SagaGateway();
    }

    @Bean
    @ConditionalOnClass(ConsumerFactory.class)
    public SagaKafkaListener createKafkaListener(){
        return new SagaKafkaListener();
    }
}
