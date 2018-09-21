package com.kiranreddy.budgettracker.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "transaction")
public class Transaction {

	@Id
	private String id;

	private String type;

	private Double amount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;

	private String note;

	private String category;

	private String userId;

	public Transaction() {
	}

	public Transaction(String id, String type, Double amount, Date date, String note, String category) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.date = date;
		this.note = note;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCategory() {
		return category;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
