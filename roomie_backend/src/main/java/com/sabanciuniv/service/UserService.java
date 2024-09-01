// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.service;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sabanciuniv.exception.UserException;
import com.sabanciuniv.model.User;
import com.sabanciuniv.model.Token;
import com.sabanciuniv.repo.UserRepository;

@Service
public class UserService{

    @Autowired private UserRepository userRepository;

    public User loginUser(User user) throws Exception{
		User userFound = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if(userFound==null) {
			throw new UserException("Username or password is wrong.");
		}
		
		String tokenText = new ObjectId().toString();
		Token token = new Token(tokenText,LocalDateTime.now().plusDays(100));
		userFound.setToken(token);
		userFound = userRepository.save(userFound);

		return userFound;
		
	}
    
    public boolean registerUser(User user) throws Exception {
		
		try {
			userRepository.save(user);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new UserException("Username already exists.");
		}catch (Exception e) {
			throw e;
		}
		
		return true;
	}
    
    public User getUserByToken(String tokenStr) {
		User userFound = userRepository.findByTokenString(tokenStr);
		return userFound;
		
	}
	
	public void destroyToken(String tokenStr) {
		User userFound = userRepository.findByTokenString(tokenStr);
		
		userFound.setToken(null);
		
		userRepository.save(userFound);
		
	}
}
