package com.loki.server.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserBindCode implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private String authCode;
	private Timestamp sendTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
}
