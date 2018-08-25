package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.UserExtension;

public interface UserExtensionDao {
	void insert(UserExtension userExtension);
	boolean update(UserExtension userExtension);
	boolean delete(int id);
	List<UserExtension> findAll();
	UserExtension findById(int id);
	UserExtension findByUserId(int userId);
	boolean deleteByUserId(int userId);
	boolean updateByUserId(UserExtension userExtension);
}
