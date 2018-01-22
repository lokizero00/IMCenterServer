package com.loki.server.service;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.TradeComplex;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

public interface TradeService {
	
	//mobile
	ServiceResult<Integer> publishTrade(TradeVO tradeVO);
	ServiceResult<List<TradeComplex>> getTradeList_mobile(Map<String,Object> map);
	ServiceResult<TradeComplex> getTrade_mobile(int tradeId);
}
