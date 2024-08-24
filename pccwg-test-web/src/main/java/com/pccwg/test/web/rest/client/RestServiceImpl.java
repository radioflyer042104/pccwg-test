package com.pccwg.test.web.rest.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.pccwg.test.web.dto.UserDto;

@Service
public class RestServiceImpl implements RestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceImpl.class);
	
	private final RestClient client;
	
	public RestServiceImpl(RestClient.Builder restClientBuilder, @Value("${pccwg.rest.api.baseurl}") final String baseUrl) {
		LOGGER.info("baseUrl: " + baseUrl);
		this.client = restClientBuilder.baseUrl(baseUrl).build();	
	}
	
	@Override
	public List<UserDto> getUsers() {
		List<UserDto> dtos = this.client.get().uri("/list").retrieve().body(new ParameterizedTypeReference<>() {});
//		dtos.stream().forEach(System.out::println);
		return dtos;
	}

	@Override
	public UserDto registerUser(UserDto dto) {
		UserDto user = this.client.post()
				.uri("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.body(dto)
				.retrieve().body(new ParameterizedTypeReference<>() {});
		return user;
	}

	@Override
	public List<UserDto> updateUsers(List<UserDto> dtos) {
		List<UserDto> users = this.client.put()
				.uri("/update")
				.contentType(MediaType.APPLICATION_JSON)
				.body(dtos)
				.retrieve().body(new ParameterizedTypeReference<>() {});
		return users;
	}

	@Override
	public String deleteUsers(List<Long> ids) {
		StringBuilder sb = new StringBuilder();
		ids.stream().forEach((id) -> {
			String msg = this.client
					.delete()
					.uri("/delete/" + id)
					.retrieve().body(new ParameterizedTypeReference<>() {});
			LOGGER.info(msg);
			sb.append(msg);
		});
		return sb.toString();
	}

	@Override
	public UserDto getUser(Long userId) {
		UserDto dto = this.client.get().uri("/get/" + userId).retrieve().body(new ParameterizedTypeReference<>() {});
		System.out.println(dto);
		return dto;		
	}

}
