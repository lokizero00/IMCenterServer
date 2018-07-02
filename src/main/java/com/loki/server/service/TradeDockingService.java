package com.loki.server.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.loki.server.dto.TradeDockingDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.vo.ServiceResult;

public interface TradeDockingService {
	//mobile
	ServiceResult<Integer> addTradeDocking(TradeDockingDTO tradeDockingDTO);
	ServiceResult<Void> delTradeDocking(Integer tradeId,Integer tradeDockingId);
	ServiceResult<Integer> chooseDocking(Integer tradeId,Integer tradeDockingId); 
	ServiceResult<PagedResult<TradeDockingDTO>> getTradeDockingList(Map<String,Object> map);
	
}
