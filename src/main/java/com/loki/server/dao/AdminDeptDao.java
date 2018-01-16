package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.AdminDept;

public interface AdminDeptDao {
	void insert(AdminDept adminDept);
	boolean update(AdminDept adminDept);
	boolean delete(int id);
	List<AdminDept> findAll();
	AdminDept findById(int id);
}
