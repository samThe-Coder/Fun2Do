package com.sampad.fun2do.backend.exception.user;

public class InvalidCredentialsException extends RuntimeException {
	
	public InvalidCredentialsException(String message) {
		super(message);
	}
}
