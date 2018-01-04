package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.UserBindCode;

public interface UserBindCodeDao {
	void insert(UserBindCode userBindCode);
	UserBindCode findById(int id);
	List<UserBindCode> findAll();
	boolean delete(int id);
}
