package com.example.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.service.UserService;

@Component
public class InitDatabase {
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void initUser() {
		userService.initUser();
	}
}
