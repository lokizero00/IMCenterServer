package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.Role;

public interface RoleDao {
	void insert(Role role);
	boolean update(Role role);
	boolean delete(int id);
	List<Role> findAll();
	Role findById(int id);
}
