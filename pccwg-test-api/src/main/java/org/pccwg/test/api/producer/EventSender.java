package org.pccwg.test.api.producer;

import org.pccwg.test.api.dto.UserDto;

public interface EventSender {
	
	public void send(UserDto dto);
}
