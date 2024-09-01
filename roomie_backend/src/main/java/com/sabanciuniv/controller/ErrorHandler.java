// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sabanciuniv.exception.UserException;

@RestControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(UserException.class)
	public Payload<String> handleRegisterException(UserException ex) {
		return new Payload<String>(LocalDateTime.now(), "ERROR",ex.getMessage());
	}
	
}