package com.kiranreddy.budgettracker.category;

public class TransactionCategoryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4516397019338494265L;

	public TransactionCategoryNotFoundException(String message) {
		super(message);
	}
}
