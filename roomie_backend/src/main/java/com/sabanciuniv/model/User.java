// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	
	@Id private String id;
	@Indexed(unique = true) private String username;
	@Indexed(unique = true) private String email;
	@DBRef private Group group;
	
	private String name;
	private String password;
	private Token token;
		
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, String password, String name, String email, Token token) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.token = token;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}
