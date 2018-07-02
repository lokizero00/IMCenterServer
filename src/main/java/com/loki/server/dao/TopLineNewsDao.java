package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.TopLineNews;

public interface TopLineNewsDao {
	void insert(TopLineNews topLineNews);
	boolean delete(int id);
	List<TopLineNews> findAll();
	TopLineNews findById(int id);
	List<TopLineNews> findLastest5List();
}
