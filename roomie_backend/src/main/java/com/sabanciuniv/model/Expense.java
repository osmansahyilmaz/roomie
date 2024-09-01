// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.model;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Expense {
	@Id
	private String id;
	
	private double amount;
	private Category category;
	private String description;
	private LocalDate date;
	
	@DBRef
	private Optional<Group> group;
	
	@DBRef
	private User user;
	
	public Expense(double amount, Category category, String description) {
		super();
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.date = LocalDate.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public Optional<Group> getGroup() {
		return group;
	}

	public void setGroup(Optional<Group> optional) {
		this.group = optional;			
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user2) {
		this.user = user2;
	}
}
