package com.pccwg.test.web.rest.client;

import java.util.List;

import com.pccwg.test.web.dto.UserDto;

public interface RestService {

	public List<UserDto> getUsers();
	
	public UserDto registerUser(UserDto dto);
	
	public void updateUsers(List<UserDto> dtos);
	
	public void deleteUsers(List<Long> ids);
	
	public UserDto getUser(Long userId);
}
