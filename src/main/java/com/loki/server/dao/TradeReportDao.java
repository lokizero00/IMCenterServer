package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.TradeReport;

public interface TradeReportDao {
	void insert(TradeReport tradeReport);
	boolean update(TradeReport tradeReport);
	boolean delete(int id);
	List<TradeReport> findAll();
	TradeReport findById(int id);
}
