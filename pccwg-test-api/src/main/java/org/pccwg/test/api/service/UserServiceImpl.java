package org.pccwg.test.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.pccwg.test.api.dto.UserDto;
import org.pccwg.test.api.entity.Uzer;
import org.pccwg.test.api.exception.EmailAlreadyUsedException;
import org.pccwg.test.api.exception.UserNotFoundException;
import org.pccwg.test.api.producer.EventSender;
import org.pccwg.test.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EventSender eventSender;
	
	@Override
	public UserDto register(UserDto dto) throws EmailAlreadyUsedException {
		Uzer existing = userRepo.findByEmail(dto.getEmail());
		if (existing != null) {
			throw new EmailAlreadyUsedException(String.format("Error: Email already used: %s", dto.getEmail()));
		}
		Uzer entity = dto.toEntity();
		Uzer savedEntity = userRepo.save(entity);
		UserDto savedDto = UserDto.toDto(savedEntity);
		eventSender.send(savedDto);
		return savedDto;
	}

	@Override
	public List<UserDto> listUsers() {
		List<Uzer> entities = userRepo.findAll();
		return entities.stream().map(UserDto::toDto)
		.collect(Collectors.toList());
	}
	
	@Override
	public List<UserDto> updateUsers(List<UserDto> dtos) throws UserNotFoundException, EmailAlreadyUsedException {
		List<Long> invalidIds = new ArrayList<Long>();
		List<String> existingEmails = new ArrayList<String>();
		List<Uzer> entities = new ArrayList<Uzer>();
		dtos.stream().forEach((dto) -> {
			try {
				Uzer existing = userRepo.findByEmailAndIdNot(dto.getEmail(), dto.getId());
				if (existing != null) {
					existingEmails.add(dto.getEmail());
				} else {
					Uzer entity = userRepo.getReferenceById(dto.getId());
					if (!entity.getDeleted()) {
						entity.setName(dto.getName());
						entity.setEmail(dto.getEmail());
						entity.setPassword(dto.getPassword());
						entities.add(entity);														
					} else {
						invalidIds.add(dto.getId());
					}

				}
			} catch (EntityNotFoundException e) {
				invalidIds.add(dto.getId());
			}
		});
		if (invalidIds.size() == 0 && existingEmails.size() == 0) {
			userRepo.saveAll(entities);
		} else if (invalidIds.size() > 0) {
			throw new UserNotFoundException("Error: Invalid ids: " + invalidIds.stream().map((id) -> { return id.toString(); }).collect(Collectors.joining(",")));
		} else if (existingEmails.size() > 0) {
			throw new EmailAlreadyUsedException("Error: Emails already used: " + existingEmails.stream().collect(Collectors.joining(",")));
		}
		return dtos;
	}

	@Override
	public String deleteUsers(List<Long> userIds) throws UserNotFoundException {
		List<Long> invalidIds = new ArrayList<Long>();
		List<Uzer> entities = new ArrayList<Uzer>();
		userIds.stream().forEach((id) -> {
			try {
				Uzer entity = userRepo.getReferenceById(id);
				if (!entity.getDeleted()) {
					entity.setDeleted(true);
					entities.add(entity);													
				} else {
					invalidIds.add(id);
				}
			} catch (EntityNotFoundException e) {
				invalidIds.add(id);
			}
		});
		if (invalidIds.size() == 0) {
			userRepo.saveAll(entities);
		} else {
			throw new UserNotFoundException("Error: Invalid ids: " + invalidIds.stream().map((id) -> { return id.toString(); }).collect(Collectors.joining(",")));
		}
		return "Users marked as deleted";
	}

	@Override
	public UserDto getUser(Long userId) throws UserNotFoundException {
		try {
			return UserDto.toDto(userRepo.getReferenceById(userId));
		} catch (EntityNotFoundException e) {
			throw new UserNotFoundException("Error: Invalid id: " + userId);
		}
	}
	
}
