package com.loki.server.vo;

import java.io.Serializable;

public class IdentityCertificationVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private String trueName;
	private String identityNumber;
	private String identityFront;
	private String identityBack;
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
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	public String getIdentityFront() {
		return identityFront;
	}
	public void setIdentityFront(String identityFront) {
		this.identityFront = identityFront;
	}
	public String getIdentityBack() {
		return identityBack;
	}
	public void setIdentityBack(String identityBack) {
		this.identityBack = identityBack;
	}
}
