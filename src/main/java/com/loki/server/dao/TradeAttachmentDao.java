package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.TradeAttachment;

public interface TradeAttachmentDao {
	void insert(TradeAttachment tradeAttachment);
	boolean update(TradeAttachment tradeAttachment);
	boolean delete(int id);
	List<TradeAttachment> findAll();
	List<TradeAttachment> findByTradeId(int tradeId);
	TradeAttachment findById(int id);
}
