package com.kiranreddy.budgettracker.transaction;

public class TransactionNotFoundException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5305028778143893570L;

	public TransactionNotFoundException(String message) {
		super(message);
	}
	
}
