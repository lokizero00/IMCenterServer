package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.IntentionRechargeWexin;

public interface IntentionRechargeWexinDao {
	void insert(IntentionRechargeWexin intentionRechargeWexin);
	boolean update(IntentionRechargeWexin intentionRechargeWexin);
	boolean delete(int id);
	List<IntentionRechargeWexin> findAll();
	IntentionRechargeWexin findById(int id);
	IntentionRechargeWexin findByOutTradeNo(String outTradeNo);
}
