package uz.kassa.microservice.saga;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;

@SpringBootTest
class SagaApplicationTests {

	@Autowired
	ProducerFactory producerFactory;

	@Autowired
	ConsumerFactory consumerFactory;

	@Test
	void contextLoads() {
		Producer  producer = producerFactory.createProducer();
		try {
			producer.beginTransaction();
			producer.send(new ProducerRecord("topic_saga","12","test"));
			producer.commitTransaction();
		} catch (ProducerFencedException e) {
			producer.abortTransaction();
			e.printStackTrace();
		}

		Consumer consumer = consumerFactory.createConsumer();
		consumer.subscribe(Collections.singleton("topic_saga"));

		int count = 0;

		while (true) {
			ConsumerRecords<Object, Object> records = consumer.poll(Duration.ofSeconds(3));
			System.out.println("****** Record Count ******* : " + records.count());

			if (records.count() == 0) {
				count++;
				if (count > 3) {
					break;
				} else
					continue;
			}

			for (ConsumerRecord<Object, Object> record : records) {
				System.out.println("Message: " + record.value());
				System.out.println("Message key: " + record.key());
				System.out.println("Message offset: " + record.offset());
				System.out.println("Message headers: " + record.timestamp());

				Date date = new Date(record.timestamp());
				Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss.SSS");
				System.out.println("Message date: " + format.format(date));
			}
			consumer.commitSync();
		}
	}

}
