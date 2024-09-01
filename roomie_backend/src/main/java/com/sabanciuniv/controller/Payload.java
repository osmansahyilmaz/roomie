// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.controller;

import java.time.LocalDateTime;

public class Payload<T> {
	
	private String status;
	private LocalDateTime time;
	private T data;
	private String data2;
	
	public Payload() {
		// TODO Auto-generated constructor stub
	}
	public Payload(LocalDateTime time, String status,T data, String data2) {
		super();
		this.time = time;
		this.data = data;
		this.data2 = data2;
		this.status = status;
	}
	public Payload(LocalDateTime time, String status,T data) {
		super();
		this.time = time;
		this.data = data;
		this.status = status;
	}
	
	public Payload(LocalDateTime time, String status) {
		super();
		this.time = time;
		this.status = status;

	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}