package org.pccwg.test.api.producer;

import java.util.concurrent.CompletableFuture;

import org.pccwg.test.api.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;



@Service
public class EventSenderImpl implements EventSender {

	@Autowired
	KafkaTemplate<Integer, String> template;
	
	@Value(value = "${spring.kafka.topic}")
    private String kafkaTopic;	
	
	@Override
	public void send(UserDto dto) {
		CompletableFuture<SendResult<Integer, String>>  future = template.send(kafkaTopic, dto.getId().intValue(), "email:" + dto.getEmail() + ", name:" + dto.getName());
		future.whenComplete((result, ex) -> {
			System.out.println(String.format("sent event with id:%d and value:%s to topic:%s", result.getProducerRecord().key(), result.getProducerRecord().value(), result.getProducerRecord().topic()));			
		});
	}

}
