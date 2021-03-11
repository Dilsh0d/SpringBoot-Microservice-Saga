package uz.kassa.microservice.saga.model.convertor.saga;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import uz.kassa.microservice.saga.model.embeded.SagaData;

/**
 * @author Tadjiev Dilshod
 */
@WritingConverter
public class SagaDataToBytesConverter implements Converter<SagaData, byte[]> {

    private final GenericJackson2JsonRedisSerializer serializer;

    public SagaDataToBytesConverter() {
        this.serializer = new GenericJackson2JsonRedisSerializer();
    }

    @Override
    public byte[] convert(SagaData source) {
        return serializer.serialize(source);
    }
}
