package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.RoleResource;

public interface RoleResourceDao {
	void insert(RoleResource roleResource);
	boolean update(RoleResource roleResource);
	boolean delete(int id);
	List<RoleResource> findAll();
	RoleResource findById(int id);
}
