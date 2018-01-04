package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.Trade;

public interface TradeDao {
	void insert(Trade trade);
	boolean update(Trade trade);
	boolean delete(int id);
	List<Trade> findAll();
	List<Trade> findByUserId(int userId);
	Trade findById(int id);
}
