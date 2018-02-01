package com.loki.server.service;

import java.util.List;

import com.loki.server.entity.TradeLog;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

public interface TradeLogService {
	ServiceResult<List<TradeLog>> getTradeLogMobile(int tradeId);
	List<TradeLog> getTradeLog(int tradeId) throws ServiceException;
}
