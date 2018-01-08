package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.Resource;

public interface ResourceDao {
	void insert(Resource resource);
	boolean update(Resource resource);
	boolean delete(int id);
	List<Resource> findAll();
	Resource findById(int id);
}
