package uz.kassa.microservice.saga.model.convertor.saga;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import uz.kassa.microservice.saga.model.embeded.SagaData;

/**
 * @author Tadjiev Dilshod
 */
@ReadingConverter
public class BytesToSagaDataConverter implements Converter<byte[], SagaData> {
    private final GenericJackson2JsonRedisSerializer deserialize;

    public BytesToSagaDataConverter() {
        this.deserialize = new GenericJackson2JsonRedisSerializer();
    }

    @Override
    public SagaData convert(byte[] source) {
        return (SagaData) deserialize.deserialize(source);
    }
}
