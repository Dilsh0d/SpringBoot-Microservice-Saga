package uz.kassa.microservice.saga.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tadjiev Dilshod
 */
@Configuration
@ConfigurationProperties(prefix = "saga")
public class SagaConfigProperties {
    private Integer consumerEventConcurrent=5;
    private Integer sameTimeCreateInstanceWorkers=3;

    public Integer getConsumerEventConcurrent() {
        return consumerEventConcurrent;
    }

    public void setConsumerEventConcurrent(Integer consumerEventConcurrent) {
        this.consumerEventConcurrent = consumerEventConcurrent;
    }

    public Integer getSameTimeCreateInstanceWorkers() {
        return sameTimeCreateInstanceWorkers;
    }

    public void setSameTimeCreateInstanceWorkers(Integer sameTimeCreateInstanceWorkers) {
        this.sameTimeCreateInstanceWorkers = sameTimeCreateInstanceWorkers;
    }
}
