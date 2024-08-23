package org.pccwg.test.api.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class Producer {

	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	@Value(value = "${spring.kafka.topic}")
    private String kafkaTopic;	
	
	@Bean
	public ProducerFactory<Integer, String> producerFactory() {
		return new DefaultKafkaProducerFactory<Integer, String>(producerConfigs());
	}
	
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}
	
	@Bean
	public KafkaTemplate<Integer, String> kafkaTemplate() {
		return new KafkaTemplate<Integer, String>(producerFactory());
	}
}
