package org.pccwg.test.consumer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.pccwg.test.consumer.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(properties = { "spring.kafka.bootstrap-servers=localhost:9092" })
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class ApplicationTest {

	@Autowired
	private Listener consumer;
	
	@Autowired
	private KafkaProducer producer;
	
	@MockBean
	private EmailService emailService;
	
	@Value("${spring.kafka.topic}")
	private String topic;
	
	@Test
	public void testReceiveMessage() throws Exception {
		producer.send(topic, "email:audric@yahoo.com,name:audric olivar");

		doNothing().when(emailService).sendEmail("noreply@pccwg-test.com", "audric@yahoo.com", "Registration Confirmation", "Welcome audric olivar and congratulations on a successful registration!");
		consumer.getLatch().await(10, TimeUnit.SECONDS);
		verify(emailService, times(1)).sendEmail("noreply@pccwg-test.com", "audric@yahoo.com", "Registration Confirmation", "Welcome audric olivar and congratulations on a successful registration!");
	}

}
