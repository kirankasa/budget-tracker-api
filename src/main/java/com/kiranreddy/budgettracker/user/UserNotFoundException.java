package com.kiranreddy.budgettracker.user;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8046450617201430310L;
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
}
