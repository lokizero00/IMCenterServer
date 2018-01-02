package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.AdminLog;

public interface AdminLogDao {
	void insert(AdminLog adminLog);
	boolean update(AdminLog adminLog);
	boolean delete(int id);
	List<AdminLog> findAll();
	AdminLog findById(int id);
	List<AdminLog> findByAdminId(int adminId);
}
