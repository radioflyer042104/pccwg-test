package com.pccwg.test.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
