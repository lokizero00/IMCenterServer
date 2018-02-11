package com.loki.server.service;

import java.math.BigDecimal;
import java.util.Map;

import com.loki.server.entity.PagedResult;
import com.loki.server.entity.TradeComplex;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

public interface TradeService {
	//admin
	//贸易审核
	void tradeVerify(int tradeId,String tradeStatus,String refuseReason,int adminId) throws ServiceException;
	PagedResult<TradeComplex> getTradeList(Map<String, Object> map) throws ServiceException;
	TradeComplex getTrade(int tradeId) throws ServiceException;
	
	//mobile部分
	//发布贸易(新增+上架)
	ServiceResult<Integer> publishTrade(TradeVO tradeVO);
	ServiceResult<TradeComplex> getTrade_mobile(int tradeId);
	//上架贸易
	ServiceResult<Void> setTradeOnShelves(int tradeId,int userId,BigDecimal tradeIntention);
	//下架贸易
	ServiceResult<Void> setTradeUnderCarriage(int tradeId,int userId);
	//新增贸易
	ServiceResult<Integer> addTrade(TradeVO tradeVO);
	//修改贸易
	ServiceResult<Void> editTrade(TradeVO tradeVO);
	ServiceResult<PagedResult<TradeComplex>> getTradeList_mobile(Map<String, Object> map,Integer pageNo,Integer pageSize);
	
}
