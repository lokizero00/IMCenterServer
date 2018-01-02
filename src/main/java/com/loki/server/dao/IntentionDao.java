package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.Intention;

public interface IntentionDao {
	void insert(Intention intention);
	boolean update(Intention intention);
	boolean delete(int id);
	List<Intention> findAll();
	Intention findById(int id);
	Intention findByUserId(int userId);
}
