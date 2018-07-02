package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.TradeDocking;

public interface TradeDockingDao {
	void insert(TradeDocking tradeDocking);
	boolean update(TradeDocking tradeDocking);
	TradeDocking findById(int id);
	List<TradeDocking> findByTradeId(int tradeId);
	List<TradeDocking> findByUserId(int userId);
	List<TradeDocking> findAll();
	boolean delete(int id);
	TradeDocking findByIdAndUserId(@Param("id") int id,@Param("userId") int userId);
	List<TradeDocking> findByParam(Map<String,Object> map);
	int countByTradeAndUserId(@Param("tradeId") int tradeId,@Param("userId") int userId);
}
