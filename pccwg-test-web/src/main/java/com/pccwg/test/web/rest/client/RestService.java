package com.pccwg.test.web.rest.client;

import java.util.List;

import com.pccwg.test.web.dto.UserDto;

public interface RestService {

	public List<UserDto> getUsers();
	
	public UserDto registerUser(UserDto dto);
	
	public List<UserDto> updateUsers(List<UserDto> dtos);
	
	public String deleteUsers(List<Long> ids);
	
	public UserDto getUser(Long userId);
}
