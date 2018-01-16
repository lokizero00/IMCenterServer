package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.UserToken;

public interface UserTokenDao {
	void insert(UserToken userToken);
	boolean delete(int id);
	boolean expireByUserId(int userId);
	List<UserToken> findAll();
	List<UserToken> findByUserId(int userId);
	UserToken findByToken(String token);
	int tokenCheck(String token);
}
