package com.loki.server.service;

import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

public interface TradeService {
	ServiceResult<Integer> publishTrade(TradeVO tradeVO);
}
