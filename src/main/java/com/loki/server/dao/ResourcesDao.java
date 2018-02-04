package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.Resources;

public interface ResourcesDao {
	void insert(Resources resource);
	boolean update(Resources resource);
	boolean delete(int id);
	List<Resources> findAll();
	Resources findById(int id);
}
