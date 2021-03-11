package uz.kassa.microservice.saga.model.convertor.event;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import uz.kassa.microservice.saga.model.embeded.EventsData;

/**
 * @author Tadjiev Dilshod
 */
@ReadingConverter
public class BytesToEventsDataConverter implements Converter<byte[],EventsData> {

    private final GenericJackson2JsonRedisSerializer serializer;

    public BytesToEventsDataConverter() {
        this.serializer = new GenericJackson2JsonRedisSerializer();
    }

    @Override
    public EventsData convert(byte[] bytes) {
        return (EventsData) serializer.deserialize(bytes);
    }
}
