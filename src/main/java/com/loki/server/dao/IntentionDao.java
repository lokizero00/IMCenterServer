package com.loki.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.loki.server.entity.Intention;

public interface IntentionDao {
	void insert(Intention intention);
	boolean update(Intention intention);
	boolean delete(int id);
	List<Intention> findAll();
	Intention findById(int id);
	Intention findByUserId(int userId);
	BigDecimal findAvailableByUserId(int userId);
}
