package com.kiranreddy.budgettracker.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction_category")
public class TransactionCategory {

	@Id
	private String id;

	private String category;

	private String userId;

	public TransactionCategory() {
	}

	public TransactionCategory(String id, String category) {
		this.id = id;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
