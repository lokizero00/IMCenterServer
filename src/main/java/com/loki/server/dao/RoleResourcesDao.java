package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.RoleResources;

public interface RoleResourcesDao {
	void insert(RoleResources roleResources);
	boolean update(RoleResources roleResources);
	boolean delete(int id);
	List<RoleResources> findAll();
	RoleResources findById(int id);
	List<RoleResources> findByRoleId(int roleId);
}
