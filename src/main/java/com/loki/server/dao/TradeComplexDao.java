package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.TradeComplex;

public interface TradeComplexDao {
	List<TradeComplex> findByParam(Map<String,Object> map);
	TradeComplex findById(int id);
}
