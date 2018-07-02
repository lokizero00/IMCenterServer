package com.loki.server.dao;

import java.util.List;
import java.util.Map;

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
	String findNickNameById(int id);
	String findUserNameById(int id);
	List<Integer> findIdList(@Param("status") String status);
	List<User> findByParam(Map<String,Object> map);
	User findByPhone(String phone);
	int findIdByEaseId(String easeId);
	String findEaseIdById(int userId);
}
