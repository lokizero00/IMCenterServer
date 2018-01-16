package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.IntentionRecharge;

public interface IntentionRechargeDao {
	void insert(IntentionRecharge intentionRecharge);
	boolean update(IntentionRecharge intentionRecharge);
	IntentionRecharge findById(int id);
	List<IntentionRecharge> findAll();
	boolean delete(int id);
}
