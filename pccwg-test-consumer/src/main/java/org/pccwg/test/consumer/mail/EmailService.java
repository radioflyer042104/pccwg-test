package org.pccwg.test.consumer.mail;

public interface EmailService {

	public void sendEmail(String from, String to, String subject, String message);
	
	
}
