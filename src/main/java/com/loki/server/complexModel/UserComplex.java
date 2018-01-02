package com.loki.server.complexModel;

import java.io.Serializable;

import com.loki.server.model.User;
import com.loki.server.model.UserToken;

public class UserComplex implements Serializable{

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
