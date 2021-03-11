package uz.kassa.microservice.saga.model.convertor.event;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import uz.kassa.microservice.saga.model.embeded.EventsData;

/**
 * @author Tadjiev Dilshod
 */
@WritingConverter
public class EventsDataToBytesConverter implements Converter<EventsData, byte[]> {

    private final GenericJackson2JsonRedisSerializer serializer;

    public EventsDataToBytesConverter() {
        this.serializer = new GenericJackson2JsonRedisSerializer();
    }

    @Override
    public byte[] convert(EventsData source) {
        return serializer.serialize(source);
    }
}
