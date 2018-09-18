package com.loki.server.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.dto.TradeDockingDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.TradeComplex;
import com.loki.server.service.TradeDockingService;
import com.loki.server.service.TradeService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

@Controller
@RequestMapping("/s/api/trade")
public class TradeMobileController {
	@Autowired TradeService tradeService;
	@Autowired TradeDockingService tradeDockingService;
	
	//发布贸易（自动上架）
	@RequestMapping(value="/publishTrade",method=RequestMethod.POST)
	public String publishTrade(HttpServletRequest request, @RequestBody TradeVO tradeVO,ModelMap mm) {
		ServiceResult<Integer> returnValue=tradeService.publishTrade(tradeVO);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//获取单个贸易（需求/供应）
	@RequestMapping(value="/getTrade",method=RequestMethod.GET)
	public String getTrade(HttpServletRequest request, Integer tradeId,Integer userId,ModelMap mm) {
		ServiceResult<TradeComplex> returnValue=tradeService.getTrade_mobile(tradeId,userId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//我的发布
	@RequestMapping(value="/getOwnPublishTradeList",method=RequestMethod.GET)
	public String getOwnPublishTradeList(HttpServletRequest request, Integer userId,String sn,String title,String type,Boolean ignoreSuccessAndDown,String provinceName,String cityName,String townName,String status,String invoiceCode,String industryCode,String payCode,String identityStatus,String enterpriseStatus,Integer pageNo,Integer pageSize,String sortName,String sortOrder,ModelMap mm) {
		HashMap<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		map.put("sn", sn);
		map.put("title", title);
		map.put("type", type);
		map.put("provinceName", provinceName);
		map.put("cityName", cityName);
		map.put("townName", townName);
		map.put("status", status);
		map.put("invoiceCode", invoiceCode);
		map.put("industryCode", industryCode);
		map.put("payCode", payCode);
		map.put("identityStatus", identityStatus);
		map.put("enterpriseStatus",enterpriseStatus);
		map.put("sortName", sortName);
		map.put("sortOrder", sortOrder);
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		map.put("ignoreSuccessAndDown", false);
		
		ServiceResult<PagedResult<TradeComplex>> returnValue=tradeService.getOwnPublishTradeList_mobile(map);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//获取贸易列表（需求/供应）
	@RequestMapping(value="/getTradeList",method=RequestMethod.GET)
	public String getTradeList(HttpServletRequest request, Integer userId,String sn,String title,String type,Boolean ignoreSuccessAndDown,String provinceName,String cityName,String townName,String status,String invoiceCode,String industryCode,String payCode,String identityStatus,String enterpriseStatus,Integer pageNo,Integer pageSize,String sortName,String sortOrder,ModelMap mm) {
		HashMap<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		map.put("sn", sn);
		map.put("title", title);
		map.put("type", type);
		map.put("provinceName", provinceName);
		map.put("cityName", cityName);
		map.put("townName", townName);
		map.put("status", status);
		map.put("invoiceCode", invoiceCode);
		map.put("industryCode", industryCode);
		map.put("payCode", payCode);
		map.put("identityStatus", identityStatus);
		map.put("enterpriseStatus",enterpriseStatus);
		map.put("sortName", sortName);
		map.put("sortOrder", sortOrder);
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		ignoreSuccessAndDown=ignoreSuccessAndDown==null? false:ignoreSuccessAndDown;
		map.put("ignoreSuccessAndDown", ignoreSuccessAndDown);
		
		ServiceResult<PagedResult<TradeComplex>> returnValue=tradeService.getTradeList_mobile(map);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//新增贸易（需要手动上架）
	@RequestMapping(value="/addTrade",method=RequestMethod.POST)
	public String addTrade(HttpServletRequest request,@RequestBody TradeVO tradeVO,ModelMap mm) {
		ServiceResult<Integer> returnValue=tradeService.addTrade(tradeVO);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//修改贸易
	@RequestMapping(value="/editTrade",method=RequestMethod.POST)
	public String editTrade(HttpServletRequest request,@RequestBody TradeVO tradeVO,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.editTrade(tradeVO);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易上架
	@RequestMapping(value="/setTradeOnShelves",method=RequestMethod.POST)
	public String setTradeOnShelves(HttpServletRequest request,int tradeId,int userId,BigDecimal tradeIntention,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.setTradeOnShelves(tradeId,userId,tradeIntention);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易下架
	@RequestMapping(value="/setTradeUnderCarriage",method=RequestMethod.POST)
	public String setTradeUnderCarriage(HttpServletRequest request,int tradeId,int userId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.setTradeUnderCarriage(tradeId, userId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//获取收藏列表
	@RequestMapping(value="/getCollectionTrade",method=RequestMethod.GET)
	public String getCollectionTrade(HttpServletRequest request, Integer userId,String type,Integer pageNo,Integer pageSize,String sortName,String sortOrder,ModelMap mm) {
		HashMap<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		map.put("type", type);
		map.put("sortName", sortName);
		map.put("sortOrder", sortOrder);
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		
		ServiceResult<PagedResult<TradeComplex>> returnValue=tradeService.getCollectionTrade(map);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易对接
	@RequestMapping(value="/addTradeDocking",method=RequestMethod.POST)
	public String addTrackDocking(HttpServletRequest request,@RequestBody TradeDockingDTO tradeDockingDTO,ModelMap mm) {
		ServiceResult<Integer> returnValue=tradeDockingService.addTradeDocking(tradeDockingDTO);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易招标中，对接方取消
	@RequestMapping(value="/delTradeDocking",method=RequestMethod.POST)
	public String delTrackDocking(HttpServletRequest request,Integer tradeId,Integer tradeDockingId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeDockingService.delTradeDocking(tradeId, tradeDockingId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易招标中，发布方选择对接
	@RequestMapping(value="/chooseTradeDocking",method=RequestMethod.POST)
	public String chooseTrackDocking(HttpServletRequest request,Integer tradeId,Integer tradeDockingId,ModelMap mm) {
		ServiceResult<Integer> returnValue=tradeDockingService.chooseDocking(tradeId, tradeDockingId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易对接中，发布方取消对接申请
	@RequestMapping(value="/tradeOwnCancel",method=RequestMethod.POST)
	public String tradeOwnCancel(HttpServletRequest request,Integer userId,Integer tradeId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.tradeOwnCancel(userId, tradeId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易对接中，对接方取消对接申请
	@RequestMapping(value="/trade3rdCancel",method=RequestMethod.POST)
	public String trade3rdCancel(HttpServletRequest request,Integer userId,Integer tradeId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.trade3rdCancel(userId, tradeId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易对接中，任意一方取消对接，需要进行确认取消
	@RequestMapping(value="/tradeCancelConfirm",method=RequestMethod.POST)
	public String tradeCancelConfirm(HttpServletRequest request,Integer userId,Integer tradeId,Integer tradeDockingId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.tradeCancelConfirm(userId, tradeId, tradeDockingId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易对接中，对接方确认成功
	@RequestMapping(value="/trade3rdSuccess",method=RequestMethod.POST)
	public String trade3rdSuccess(HttpServletRequest request,Integer userId,Integer tradeId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.trade3rdSuccess(userId, tradeId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//贸易对接中，发布方确认成功
	@RequestMapping(value="/tradeOwnSuccess",method=RequestMethod.POST)
	public String tradeOwnSuccess(HttpServletRequest request,Integer userId,Integer tradeId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.tradeOwnSuccess(userId, tradeId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//贸易对接中，任意一方确认成功，需要另一方进行确认
	@RequestMapping(value="/tradeSuccessConfirm",method=RequestMethod.POST)
	public String tradeSuccessConfirm(HttpServletRequest request,Integer userId,Integer tradeId,Integer tradeDockingId,ModelMap mm) {
		ServiceResult<Void> returnValue=tradeService.tradeSuccessConfirm(userId, tradeId, tradeDockingId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//获取贸易对接列表
	@RequestMapping(value="/getTradeDockingList",method=RequestMethod.GET)
	public String getTradeDockingList(HttpServletRequest request,Integer tradeId,Integer userId,String type,Integer pageNo, Integer pageSize,ModelMap mm) {
		Map<String,Object> map=new HashMap<>();
		map.put("tradeId", tradeId);
		map.put("userId", userId);
		map.put("type", type);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		ServiceResult<PagedResult<TradeDockingDTO>> returnValue=tradeDockingService.getTradeDockingList(map);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//获取推荐贸易列表
	@RequestMapping(value="/getRecommendedTradeList",method=RequestMethod.GET)
	public String getRecommendedTradeList(HttpServletRequest request, String title,String type,String provinceName,String cityName,String townName,String status,String invoiceCode,String industryCode,String payCode,Integer pageNo,Integer pageSize,ModelMap mm) {
		HashMap<String,Object> map=new HashMap<>();
		map.put("title", title);
		map.put("type", type);
		map.put("provinceName", provinceName);
		map.put("cityName", cityName);
		map.put("townName", townName);
		map.put("status", status);
		map.put("invoiceCode", invoiceCode);
		map.put("industryCode", industryCode);
		map.put("payCode", payCode);
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		
		ServiceResult<PagedResult<TradeComplex>> returnValue=tradeService.getRecommendedList(map);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//获取最新发布
	@RequestMapping(value="/getLastest10TradeList",method=RequestMethod.GET)
	public String getLastest10TradeList(HttpServletRequest request,ModelMap mm) {
		ServiceResult<List<TradeComplex>> returnValue=tradeService.getLastest10Trade();
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//获取我的对接贸易列表（需求/供应）
	@RequestMapping(value="/getDockingTradeList",method=RequestMethod.GET)
	public String getDockingTradeList(HttpServletRequest request, Integer userId,String sn,String title,String type,String provinceName,String cityName,String townName,String status,String invoiceCode,String industryCode,String payCode,Integer pageNo,Integer pageSize,String sortName,String sortOrder,ModelMap mm) {
		HashMap<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		map.put("sn", sn);
		map.put("title", title);
		map.put("type", type);
		map.put("provinceName", provinceName);
		map.put("cityName", cityName);
		map.put("townName", townName);
		map.put("status", status);
		map.put("invoiceCode", invoiceCode);
		map.put("industryCode", industryCode);
		map.put("payCode", payCode);
		map.put("sortName", sortName);
		map.put("sortOrder", sortOrder);
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		
		ServiceResult<PagedResult<TradeComplex>> returnValue=tradeService.getDockingTradeList_mobile(map);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	//获取推荐贸易列表
	@RequestMapping(value="/getSuccessedTradeList",method=RequestMethod.GET)
	public String getSuccessedTradeList(HttpServletRequest request, String type,Integer pageNo,Integer pageSize,ModelMap mm) {
		HashMap<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		
		ServiceResult<PagedResult<TradeComplex>> returnValue=tradeService.getSuccessedList(map);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
}
