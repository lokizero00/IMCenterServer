package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.TradeComplex;

public interface TradeComplexDao {
	List<TradeComplex> findByParam(Map<String,Object> map);
	TradeComplex findById(int id);
	List<TradeComplex> findLastest10Trade();
	List<TradeComplex> findRecommendedList(Map<String,Object> map);
	List<TradeComplex> findDockingTrade(Map<String,Object> map);
	List<TradeComplex> findByOwnPublish(Map<String,Object> map);
}
