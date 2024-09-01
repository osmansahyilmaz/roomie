// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.controller;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sabanciuniv.model.User;
import com.sabanciuniv.repo.UserRepository;
import com.sabanciuniv.exception.UserException;
import com.sabanciuniv.model.Token;
import com.sabanciuniv.service.UserService;



@RestController
@RequestMapping("/roomies/auth")
public class AuthController {
	
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    
    Logger logger = LoggerFactory.getLogger(AuthController.class);
 
    
    @PostMapping("/register")
	public Payload<String> registerUser(@RequestBody User user) throws UserException{
		
		if(user.getUsername()==null ||  
				user.getPassword()==null || 
				user.getName()==null) {
			logger.error("User field problem");
			throw new UserException("All fields are required.");
		}
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new UserException("Username is already taken!");
		}
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new UserException("Email is already taken!");
		}
		try {
			userService.registerUser(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
		
			throw new UserException(e.getMessage());
		}
		
		return new Payload<String>(LocalDateTime.now(),"OK", "User registered.");
	}

    @PostMapping("/login")
	public Payload<Token> loginUser(@RequestBody User user){
		
		try {
			user = userService.loginUser(user);
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
		
		return new Payload<Token>(LocalDateTime.now(),"OK", user.getToken(),user.getId());
		
	}
    
    @GetMapping("/logout")
	public Payload<String> logoutUser(@RequestHeader String token){
		userService.destroyToken(token);
		
		
		return new Payload<String>(LocalDateTime.now(),"OK","Token destroyed.");
		
	}
}