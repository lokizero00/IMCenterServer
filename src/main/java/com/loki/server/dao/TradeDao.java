package com.loki.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.Trade;

public interface TradeDao {
	void insert(Trade trade);
	boolean update(Trade trade);
	boolean delete(int id);
	List<Trade> findAll();
	List<Trade> findByUserId(int userId);
	Trade findById(int id);
	Trade findByIdAndUserId(@Param("id") int id,@Param("userId") int userId);
	boolean updateCollectionCountAdd(int id);
	boolean updateCollectionCountSub(int id);
	boolean updateReadCountAdd(int id);
}
