package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.UserNotice;

public interface UserNoticeDao {
	void insert(UserNotice userNotice);
	boolean update(UserNotice userNotice);
	UserNotice findById(int id);
	List<UserNotice> findAll();
	boolean delete(int id);
}
