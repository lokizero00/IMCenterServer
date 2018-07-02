package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.TradeReportAttachment;

public interface TradeReportAttachmentDao {
	void insert(TradeReportAttachment tradeReportAttachment);
	boolean update(TradeReportAttachment tradeReportAttachment);
	boolean delete(int id);
	List<TradeReportAttachment> findAll();
	TradeReportAttachment findById(int id);
	List<TradeReportAttachment> findByInformationId(int tradeReportInformationId);
}
