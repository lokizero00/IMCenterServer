package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.RoleAdmin;

public interface RoleAdminDao {
	void insert(RoleAdmin roleAdmin);
	boolean update(RoleAdmin roleAdmin);
	boolean delete(int id);
	List<RoleAdmin> findAll();
	RoleAdmin findById(int id);
	RoleAdmin findByAdminId(int adminId);
	boolean deleteByAdminId(int adminId);
}
