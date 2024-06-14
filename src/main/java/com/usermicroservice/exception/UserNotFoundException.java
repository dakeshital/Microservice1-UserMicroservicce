package com.usermicroservice.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Resource not found on server !!");
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
