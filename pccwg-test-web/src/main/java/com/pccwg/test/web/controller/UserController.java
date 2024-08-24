package com.pccwg.test.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pccwg.test.web.dto.UserDto;
import com.pccwg.test.web.rest.client.RestService;


@Controller
public class UserController {

	@Autowired
	private RestService restService;
	
	@RequestMapping("/")
	public String home(Model model) {
		return "users";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		return "register";
	}
	
	@GetMapping("/update/{id}")
	public String update(Model model, @PathVariable("id") Long userId) {
		model.addAttribute("user", restService.getUser(userId));
		return "update";
	}
	
	@GetMapping(
			value="listUsers",
			produces="application/json"
	)
	public ResponseEntity<List<UserDto>> listUsers() {
		return ResponseEntity.ok().body(restService.getUsers());
	}
	
	@PostMapping(
			value="registerUser",
			produces="application/json"
	)
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user) {
		return ResponseEntity.ok().body(restService.registerUser(user));
	}
	
	@PutMapping(
			value="updateUser",
			produces="application/json"
	)
	public ResponseEntity<List<UserDto>> updateUser(@RequestBody List<UserDto> users) {
		return ResponseEntity.ok().body(restService.updateUsers(users));
	}
	
	@DeleteMapping(value="deleteUsers")
	public ResponseEntity<String> deleteUsers(@RequestBody List<Long> userIds) {
		return ResponseEntity.ok().body(restService.deleteUsers(userIds));
	}	
}
