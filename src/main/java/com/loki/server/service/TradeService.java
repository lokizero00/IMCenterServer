package com.loki.server.service;

import java.math.BigDecimal;
import java.util.List;
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
	ServiceResult<TradeComplex> getTrade_mobile(Integer tradeId,Integer userId);
	//上架贸易
	ServiceResult<Void> setTradeOnShelves(int tradeId,int userId,BigDecimal tradeIntention);
	//下架贸易
	ServiceResult<Void> setTradeUnderCarriage(int tradeId,int userId);
	//新增贸易
	ServiceResult<Integer> addTrade(TradeVO tradeVO);
	//修改贸易
	ServiceResult<Void> editTrade(TradeVO tradeVO);
	//获取贸易列表
	ServiceResult<PagedResult<TradeComplex>> getTradeList_mobile(Map<String, Object> map);
	//我的发布
	ServiceResult<PagedResult<TradeComplex>> getOwnPublishTradeList_mobile(Map<String, Object> map);
	//获取我的对接贸易列表
	ServiceResult<PagedResult<TradeComplex>> getDockingTradeList_mobile(Map<String, Object> map);
	ServiceResult<PagedResult<TradeComplex>> getCollectionTrade(Map<String,Object> map);
	//对接中，发布方取消
	ServiceResult<Void> tradeOwnCancel(Integer userId,Integer tradeId);
	//对接中，对接方取消
	ServiceResult<Void> trade3rdCancel(Integer userId,Integer tradeId);
	//对接中，任意一方取消后，另一方确认取消
	ServiceResult<Void> tradeCancelConfirm(Integer userId,Integer tradeId,Integer tradeDockingId);
	//对接中，发布方成功
	ServiceResult<Void> tradeOwnSuccess(Integer userId,Integer tradeId);
	//对接中，对接方成功
	ServiceResult<Void> trade3rdSuccess(Integer userId,Integer tradeId);
	//对接中，任意一方成功，另一方确认成功
	ServiceResult<Void> tradeSuccessConfirm(Integer userId,Integer tradeId,Integer tradeDockingId);
	//最新发布
	ServiceResult<List<TradeComplex>> getLastest10Trade();
	//为您推荐
	ServiceResult<PagedResult<TradeComplex>> getRecommendedList(Map<String, Object> map);
}
