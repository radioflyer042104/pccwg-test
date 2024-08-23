package org.pccwg.test.api.controller;

import java.util.List;

import org.pccwg.test.api.dto.UserDto;
import org.pccwg.test.api.exception.EmailAlreadyUsedException;
import org.pccwg.test.api.exception.UserNotFoundException;
import org.pccwg.test.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
    @PostMapping(path = "/register")
    public ResponseEntity<UserDto> register(@Validated @RequestBody UserDto user) throws EmailAlreadyUsedException {
    	UserDto registeredUser = userService.register(user);
        return ResponseEntity.ok().body(registeredUser);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<UserDto>> listUsers() {
    	return ResponseEntity.ok().body(userService.listUsers());
    }

    @PutMapping(path = "/update")
    public ResponseEntity<List<UserDto>> updateUsers(@RequestBody @NotEmpty(message = "users cannot be empty")List<@Valid UserDto> users) throws UserNotFoundException, EmailAlreadyUsedException {
    	return ResponseEntity.ok().body(userService.updateUsers(users));
    }
    
    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteUsers(@RequestBody @NotEmpty(message = "ids cannot be empty")List<Long> userIds) throws UserNotFoundException {
    	return ResponseEntity.ok().body(userService.deleteUsers(userIds));
    }    

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long userId) throws UserNotFoundException {
    	return ResponseEntity.ok().body(userService.getUser(userId));
    }
}
