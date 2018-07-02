package com.loki.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.TradeReport;

public interface TradeReportDao {
	void insert(TradeReport tradeReport);
	boolean update(TradeReport tradeReport);
	boolean delete(int id);
	List<TradeReport> findAll();
	TradeReport findById(int id);
	int getCountByTradeId(int tradeId);
	TradeReport findByIdAndInformerId(@Param("tradeReportId") int tradeReportId,@Param("informerId") int informerId);
	List<TradeReport> findByTradeId(int tradeId);
}
