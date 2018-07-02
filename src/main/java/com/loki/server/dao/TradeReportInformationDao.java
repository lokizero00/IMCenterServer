package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.TradeReportInformation;

public interface TradeReportInformationDao {
	void insert(TradeReportInformation tradeReportInformation);
	boolean update(TradeReportInformation tradeReportInformation);
	boolean delete(int id);
	List<TradeReportInformation> findAll();
	TradeReportInformation findById(int id);
	List<TradeReportInformation> findByTradeReportId(int tradeReportId);
}
