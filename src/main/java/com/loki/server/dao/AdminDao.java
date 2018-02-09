package com.loki.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.Admin;

public interface AdminDao {
	void insert(Admin admin);
	boolean update(Admin admin);
	boolean delete(int id);
	List<Admin> findAll();
	Admin findById(int id);
	Admin loginCheck(@Param("userName") String userName,@Param("password") String password);
	String findAdminNameById(int id);
}
