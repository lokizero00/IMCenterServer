package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.Resources;

public interface ResourcesDao {
	void insert(Resources resource);
	boolean update(Resources resource);
	boolean delete(int id);
	List<Resources> findAll();
	Resources findById(int id);
	List<Resources>findByParam(Map<String,Object> map);
	List<Resources> findByParamRoleId(Map<String,Object> map);
}
