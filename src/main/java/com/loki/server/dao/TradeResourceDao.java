package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.TradeResource;

public interface TradeResourceDao {
	void insert(TradeResource tradeResource);
	boolean update(TradeResource tradeResource);
	boolean delete(int id);
	List<TradeResource> findAll();
	TradeResource findById(int id);
}
