package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.TradeLog;

public interface TradeLogDao {
	void insert(TradeLog tradeLog);
	boolean delete(int id);
	List<TradeLog> findAll();
	TradeLog findById(int id);
	List<TradeLog> findByTradeId(int tradeId);
}
