package com.loki.server.service;

import java.util.List;

import com.loki.server.model.UserToken;

public interface UserTokenService {
	void insert(UserToken userToken);
	boolean delete(int id);
	boolean expireByUserId(int userId);
	List<UserToken> findAll();
	List<UserToken> findByUserId(int userId);
	int tokenCheck(String token);
}
