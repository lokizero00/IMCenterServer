package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.IntentionRechargeAli;

public interface IntentionRechargeAliDao {
	void insert(IntentionRechargeAli intentionRechargeAli);
	boolean update(IntentionRechargeAli intentionRechargeAli);
	boolean delete(int id);
	List<IntentionRechargeAli> findAll();
	IntentionRechargeAli findById(int id);
	IntentionRechargeAli findByOutTradeNo(String outTradeNo);
}
