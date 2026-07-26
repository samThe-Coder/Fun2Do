package com.sampad.fun2do.backend.exception.user;

public class UserAlreadyExistsException extends RuntimeException {
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
