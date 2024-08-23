package org.pccwg.test.topic;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TopicConfiguration {
	
	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	@Value(value = "${spring.kafka.topic}")
    private String kafkaTopic;
	
	@Bean
	public KafkaAdmin admin() {
		KafkaAdmin admin = null;
		try {
			Map<String, Object> configs = new HashMap<String, Object>();
			configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
			admin = new KafkaAdmin(configs);
			admin.setFatalIfBrokerNotAvailable(true);
		} catch (Exception e) {
			System.out.println("error while initializing kafka: " + e.getMessage());
		}
		return admin;
	}
	
	@Bean
	public NewTopic topic1() {
		NewTopic topic = null;
		try {
			topic = TopicBuilder.name(kafkaTopic).build();	
		} catch (Exception e) {
			System.out.println("error while initializing kafka: " + e.getMessage());
		}
		return topic;
	}
}
