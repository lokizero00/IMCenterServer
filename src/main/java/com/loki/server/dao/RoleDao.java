package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.Role;

public interface RoleDao {
	void insert(Role role);
	boolean update(Role role);
	boolean delete(int id);
	List<Role> findAll();
	Role findById(int id);
	List<Role> findByParam(Map<String,Object> map);
}
