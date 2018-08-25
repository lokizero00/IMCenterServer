package com.loki.server.entity;

import java.io.Serializable;

public class UserExtension implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private String aliAccount;
	private int aliBind;
	private String wechatAccount;
	private int wechatBind;
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
	public String getAliAccount() {
		return aliAccount;
	}
	public void setAliAccount(String aliAccount) {
		this.aliAccount = aliAccount;
	}
	public int getAliBind() {
		return aliBind;
	}
	public void setAliBind(int aliBind) {
		this.aliBind = aliBind;
	}
	public String getWechatAccount() {
		return wechatAccount;
	}
	public void setWechatAccount(String wechatAccount) {
		this.wechatAccount = wechatAccount;
	}
	public int getWechatBind() {
		return wechatBind;
	}
	public void setWechatBind(int wechatBind) {
		this.wechatBind = wechatBind;
	}
}
