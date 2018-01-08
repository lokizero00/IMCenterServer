package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.Dept;

public interface DeptDao {
	void insert(Dept dept);
	boolean update(Dept dept);
	boolean delete(int id);
	List<Dept> findAll();
	Dept findById(int id);
}
