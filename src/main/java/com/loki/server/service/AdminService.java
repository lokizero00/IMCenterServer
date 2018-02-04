package com.loki.server.service;

import java.util.List;

import com.loki.server.entity.Admin;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.AdminVO;


public interface AdminService {
	void insert(Admin admin);
	boolean update(Admin admin);
	boolean delete(int id);
	Admin findById(int id);
	List<Admin> findAll();
	AdminVO login(String userName,String password,String clientIP) throws ServiceException;
}
