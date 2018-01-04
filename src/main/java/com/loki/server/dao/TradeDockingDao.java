package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.TradeDocking;

public interface TradeDockingDao {
	void insert(TradeDocking tradeDocking);
	boolean update(TradeDocking tradeDocking);
	TradeDocking findById(int id);
	List<TradeDocking> findByTradeId(int tradeId);
	List<TradeDocking> findByUserId(int userId);
	List<TradeDocking> findAll();
	boolean delete(int id);
}
