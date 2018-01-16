package com.loki.server.vo;

import java.io.Serializable;

import com.loki.server.entity.User;
import com.loki.server.entity.UserToken;

public class UserLoginVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private User user;
	private UserToken userToken;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserToken getUserToken() {
		return userToken;
	}
	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}
}
