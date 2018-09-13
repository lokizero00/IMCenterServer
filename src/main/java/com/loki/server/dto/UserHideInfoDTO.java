package com.loki.server.dto;

import java.io.Serializable;

public class UserHideInfoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String trueName;
	private String position;
	private String phone;
	private String enterpriseName;
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
}
