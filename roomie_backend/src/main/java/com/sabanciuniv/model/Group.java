// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Group {
	@Id
	private String id;
	private String name;
	private String joinCode;
	
	List<Expense> expenses = new ArrayList<>();
	
	List<User> users = new ArrayList<>();
	

	public Group() {
		super();
	}
	public String getId() {
		return id;
	}
	public String getjoinCode() {
		return joinCode;
	}

	public void setjoinCode(String joinCode) {
		this.joinCode = joinCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	
	public void addUserToGroup(Group group, User new_user) {
		users.add(new_user);
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
