package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.IntentionLog;

public interface IntentionLogDao {
	void insert(IntentionLog intentionLog);
	boolean update(IntentionLog intentionLog);
	IntentionLog findById(int id);
	List<IntentionLog> findAll();
	boolean delete(int id);
}
