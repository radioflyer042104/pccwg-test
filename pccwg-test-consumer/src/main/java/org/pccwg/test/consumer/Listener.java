package org.pccwg.test.consumer;

import java.util.concurrent.CountDownLatch;

import org.pccwg.test.consumer.mail.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

	private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	@Autowired
	private EmailService emailService;
	
	@Value(value = "${mail.from}")
	private String from;
	
	@Value(value = "${mail.subj}")
	private String subject;
	
	@Value(value = "${mail.text}")
	private String text;
	
    @KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic}")
    public void listen(String in) {
        String[] emailAndUser = getEmailAndUser(in);
        emailService.sendEmail(from, emailAndUser[0], subject, text.replace("user", emailAndUser[1]));
        latch.countDown();
    }

	private String[] getEmailAndUser(String in) {
		String email = in.split(",")[0].split(":")[1].trim();
		String user = in.split(",")[1].split(":")[1].trim();
		return new String[] { email, user };
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
	
}
