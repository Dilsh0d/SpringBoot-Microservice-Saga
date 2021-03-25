package uz.kassa.microservice.saga.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import uz.kassa.microservice.saga.model.convertor.event.BytesToEventsDataConverter;
import uz.kassa.microservice.saga.model.convertor.event.EventsDataToBytesConverter;
import uz.kassa.microservice.saga.model.convertor.saga.BytesToSagaDataConverter;
import uz.kassa.microservice.saga.model.convertor.saga.SagaDataToBytesConverter;

import java.util.Arrays;

/**
 * @author Tadjiev Dilshod
 */
@Configuration
@EnableRedisRepositories(value = "uz.kassa.microservice.saga.model.repository")
@ComponentScan("uz.kassa.microservice.saga.model.repository")
public class RedisConfig {

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(
                new EventsDataToBytesConverter()
                ,new BytesToEventsDataConverter()
                ,new BytesToSagaDataConverter()
                ,new SagaDataToBytesConverter()));
    }
}
