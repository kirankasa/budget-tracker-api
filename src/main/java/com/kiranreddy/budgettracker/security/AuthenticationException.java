package com.kiranreddy.budgettracker.security;

public class AuthenticationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4117582054970234375L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
