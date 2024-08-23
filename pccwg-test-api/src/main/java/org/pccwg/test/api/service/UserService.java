package org.pccwg.test.api.service;

import java.util.List;

import org.pccwg.test.api.dto.UserDto;
import org.pccwg.test.api.exception.EmailAlreadyUsedException;
import org.pccwg.test.api.exception.UserNotFoundException;

public interface UserService {
	
	public UserDto register(UserDto dto) throws EmailAlreadyUsedException;
	
	public List<UserDto> listUsers();
	
	public List<UserDto> updateUsers(List<UserDto> dtos) throws UserNotFoundException, EmailAlreadyUsedException;
	
	public String deleteUsers(List<Long> userIds) throws UserNotFoundException;
	
	public UserDto getUser(Long userId) throws UserNotFoundException;
}
