package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.IntentionLog;

public interface IntentionLogDao {
	void insert(IntentionLog intentionLog);
	boolean update(IntentionLog intentionLog);
	IntentionLog findById(int id);
	List<IntentionLog> findAll();
	List<IntentionLog> findByParam(Map<String,Object> map);
	boolean delete(int id);
}
