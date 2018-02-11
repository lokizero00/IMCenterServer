package com.loki.server.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.entity.PagedResult;
import com.loki.server.entity.TradeComplex;
import com.loki.server.service.TradeService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

@Controller
@RequestMapping("/s/api/trade")
public class TradeMobileController {
	@Autowired TradeService tradeService;
	
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
	public String getTrade(HttpServletRequest request, int tradeId,ModelMap mm) {
		ServiceResult<TradeComplex> returnValue=tradeService.getTrade_mobile(tradeId);
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
	public String getTradeList(HttpServletRequest request, Integer userId,String sn,String title,String type,String provinceName,String cityName,String townName,String status,String invoiceCode,String industryCode,String payCode,Integer pageNo,Integer pageSize,ModelMap mm) {
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
		
		ServiceResult<PagedResult<TradeComplex>> returnValue=tradeService.getTradeList_mobile(map,pageNo,pageSize);
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
}
