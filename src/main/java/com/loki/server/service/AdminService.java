package com.loki.server.service;

import java.util.List;

import com.loki.server.model.Admin;


public interface AdminService {
	void insert(Admin admin);
	boolean update(Admin admin);
	boolean delete(int id);
	Admin findById(int id);
	List<Admin> findAll();
	Admin login(String userName,String password,String clientIP);
}
