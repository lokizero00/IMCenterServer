package com.loki.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.User;

public interface UserDao {
	void insert(User user);
	boolean update(User user);
	boolean delete(int id);
	User findById(int id);
	List<User> findAll();
	User loginCheck(@Param("phone") String phone,@Param("password") String password);
	int userExistCheck(String phone);
}
