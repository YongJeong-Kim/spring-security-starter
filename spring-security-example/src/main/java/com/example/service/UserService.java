package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.entity.UserRole;
import com.example.repository.UserRepository;
import com.example.repository.UserRolesRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserRolesRepository userRolesRepository;
	
	public String getUserName() {
	//	userRepository.findOne
		return "";
	}
	
	public void initUser() {
		if ( userRepository.findByUserName("user") == null) {
			User user = new User();
			user.setUserName("user");
			
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode("1234");
			
			user.setPassword(hashedPassword);
			user.setEmail("user@user.com");
			user.setEnabled(1);
			userRepository.save(user);
			
			UserRole userRole = new UserRole();
			userRole.setUserid(userRepository.findByUserName("user").getUserid());
			userRole.setRole("ROLE_ADMIN");
			
			userRolesRepository.save(userRole);
			
		}
		
	}
}
