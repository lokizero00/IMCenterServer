package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.IntentionRefund;

public interface IntentionRefundDao {
	void insert(IntentionRefund intentionRefund);
	boolean update(IntentionRefund intentionRefund);
	boolean delete(int id);
	List<IntentionRefund> findAll();
	IntentionRefund findById(int id);
}
