package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.UserNotice;

public interface UserNoticeDao {
	void insert(UserNotice userNotice);
	boolean update(UserNotice userNotice);
	UserNotice findById(int id);
	List<UserNotice> findAll();
	boolean delete(int id);
	UserNotice findByParam(Map<String, Object> map);
	List<UserNotice> findListByParam(Map<String, Object> map);
	List<Integer> findOmittedUserId(int noticeId);
}
