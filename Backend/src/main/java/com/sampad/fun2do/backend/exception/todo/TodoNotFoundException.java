package com.sampad.fun2do.backend.exception.todo;

public class TodoNotFoundException extends RuntimeException {
	public TodoNotFoundException(String message) {
		super(message);
	}
}
