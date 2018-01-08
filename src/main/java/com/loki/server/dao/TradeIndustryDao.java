package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.TradeIndustry;

public interface TradeIndustryDao {
	void insert(TradeIndustry tradeIndustry);
	boolean update(TradeIndustry tradeIndustry);
	boolean delete(int id);
	List<TradeIndustry> findAll();
	TradeIndustry findById(int id);
}
