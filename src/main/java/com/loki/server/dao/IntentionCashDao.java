package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.IntentionCash;

public interface IntentionCashDao {
	void insert(IntentionCash intentionCash);
	boolean update(IntentionCash intentionCash);
	IntentionCash findById(int id);
	List<IntentionCash> findAll();
	boolean delete(int id);
}
