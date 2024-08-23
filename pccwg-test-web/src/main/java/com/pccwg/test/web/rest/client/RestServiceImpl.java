package com.pccwg.test.web.rest.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.pccwg.test.web.dto.UserDto;

@Service
public class RestServiceImpl implements RestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceImpl.class);
	
	private final RestClient client;
	
	public RestServiceImpl(RestClient.Builder restClientBuilder) {
		this.client = restClientBuilder.baseUrl("http://pccwg-test-api:9090/api").build();	
	}
	
	@Override
	public List<UserDto> getUsers() {
		List<UserDto> dtos = this.client.get().uri("/list").retrieve().body(new ParameterizedTypeReference<>() {});
		dtos.stream().forEach(System.out::println);
		return dtos;
	}

	@Override
	public UserDto registerUser(UserDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUsers(List<UserDto> dtos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUsers(List<Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserDto getUser(Long userId) {
		UserDto dto = this.client.get().uri("/get/" + userId).retrieve().body(new ParameterizedTypeReference<>() {});
		System.out.println(dto);
		return dto;		
	}

}
