package com.loki.server.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.loki.server.entity.Dictionaries;
import com.loki.server.entity.TradeComplex;
import com.loki.server.entity.TradeLog;
import com.loki.server.service.DictionariesService;
import com.loki.server.service.TradeLogService;
import com.loki.server.service.TradeService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/trade")
public class TradeController extends BaseController{
	@Autowired TradeService tradeService;
	@Autowired DictionariesService dictionariesService;
	@Autowired TradeLogService tradeLogService;
	
//	private static final Logger logger = Logger.getLogger(TradeController.class);
	
	//获取贸易列表（需求/供应）
	@RequestMapping(value="/getTradeList",method=RequestMethod.GET)
	public String getTradeList(HttpServletRequest request, Integer userId,String sn,String title,String type,String provinceName,String cityName,String townName,String status,String invoiceCode,String industryCode,String payCode) {
		super.setHttpServletRequest(request);
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
		
		ServiceResult<List<TradeComplex>> tradeComplexListResult=tradeService.getTradeList_mobile(map);
		if (tradeComplexListResult!=null) {
			if(tradeComplexListResult.getResultCode().getCode()==ResultCodeEnums.SUCCESS.getCode()) {
				//搜索条件
				ServiceResult<List<Dictionaries>> tradeStatusQueryListResult=dictionariesService.getDictionariesListByType("trade_status");
				request.setAttribute("tradeStatusQueryList", tradeStatusQueryListResult.getResultObj());
				ServiceResult<List<Dictionaries>> tradeTypeQueryListResult=dictionariesService.getDictionariesListByType("trade_type");
				request.setAttribute("tradeTypeQueryList", tradeTypeQueryListResult.getResultObj());
				ServiceResult<List<Dictionaries>> tradeIndustryQueryListResult=dictionariesService.getDictionariesListByType("industry");
				request.setAttribute("tradeIndustryQueryList", tradeIndustryQueryListResult.getResultObj());
				ServiceResult<List<Dictionaries>> tradeInvoiceQueryListResult=dictionariesService.getDictionariesListByType("invoice");
				request.setAttribute("tradeInvoiceQueryList", tradeInvoiceQueryListResult.getResultObj());
				ServiceResult<List<Dictionaries>> tradePayQueryListResult=dictionariesService.getDictionariesListByType("paycode");
				request.setAttribute("tradePayQueryList", tradePayQueryListResult.getResultObj());
				
				//贸易列表数据
				request.setAttribute("tradeList", tradeComplexListResult.getResultObj());
			}
		}else {
			tradeComplexListResult=new ServiceResult<>();
			tradeComplexListResult.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		
		if(tradeComplexListResult.getResultCode().getCode()!=ResultCodeEnums.SUCCESS.getCode()) {
			addRequestErrorMessage(tradeComplexListResult.getResultCode().getMessage());
		}
		
		return "/trade/tradeList";
	}
	
	//获取单个贸易（需求/供应）
	@RequestMapping(value="/getTrade",method=RequestMethod.GET)
	public String getTrade(final RedirectAttributes redirectAttributes,HttpServletRequest request, int id) {
		super.setRedirectAttributes(redirectAttributes);
		ServiceResult<TradeComplex> tradeComplexResult=tradeService.getTrade_mobile(id);
		if (tradeComplexResult!=null) {
			if(tradeComplexResult.getResultCode().getCode()==ResultCodeEnums.SUCCESS.getCode()) {
				ServiceResult<List<Dictionaries>> tradeStatusListResult=dictionariesService.getDictionariesListByType("trade_status");
				ServiceResult<List<TradeLog>> tradeLogListResult=tradeLogService.getTradeLogByTradeId(id);
				request.setAttribute("trade", tradeComplexResult.getResultObj());
				request.setAttribute("tradeStatusList", tradeStatusListResult.getResultObj());
				request.setAttribute("tradeLogList", tradeLogListResult.getResultObj());
				return "/trade/tradeDetail";
			}
		}else {
			tradeComplexResult=new ServiceResult<>();
			tradeComplexResult.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		
		addErrorMessage(tradeComplexResult.getResultCode().getMessage());
		return"redirect:/s/trade/getTradeList";
	}
	
	//跳转审核页面
	@RequestMapping(value="/getTradeVerify",method=RequestMethod.GET)
	public String getTradeVerify(final RedirectAttributes redirectAttributes,HttpServletRequest request, @ModelAttribute("id") int id) {
		super.setRedirectAttributes(redirectAttributes);
		ServiceResult<TradeComplex> tradeComplexResult=tradeService.getTrade_mobile(id);
		if (tradeComplexResult!=null) {
			if(tradeComplexResult.getResultCode().getCode()==ResultCodeEnums.SUCCESS.getCode()) {
				ServiceResult<List<Dictionaries>> tradeStatusListResult=dictionariesService.getDictionariesListByType("trade_status");
				ServiceResult<List<TradeLog>> tradeLogListResult=tradeLogService.getTradeLogByTradeId(id);
				request.setAttribute("trade", tradeComplexResult.getResultObj());
				request.setAttribute("tradeStatusList", tradeStatusListResult.getResultObj());
				request.setAttribute("tradeLogList", tradeLogListResult.getResultObj());
				
				return "/trade/tradeVerify";
			}
		}else {
			tradeComplexResult=new ServiceResult<>();
			tradeComplexResult.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		
		addErrorMessage(tradeComplexResult.getResultCode().getMessage());
		return"redirect:/s/trade/getTradeList";
	}
	
	//贸易审核
	@RequestMapping(value="/tradeVerify",method=RequestMethod.POST)
	public String tradeVerify(final RedirectAttributes redirectAttributes,HttpServletRequest request,HttpSession httpSession, int tradeId, String tradeStatus, String refuseReason) {
		super.setRedirectAttributes(redirectAttributes);
		
		int adminId=(int) httpSession.getAttribute("adminId");
		ServiceResult<Void> verifyResult=tradeService.tradeVerify(tradeId, tradeStatus, refuseReason, adminId);
		if (verifyResult!=null) {
			if(verifyResult.getResultCode().getCode()==ResultCodeEnums.SUCCESS.getCode()) {
				return "redirect:/s/trade/getTradeList";
			}
		}else {
			verifyResult=new ServiceResult<>();
			verifyResult.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		
		addErrorMessage(verifyResult.getResultCode().getMessage());
		return"redirect:/s/trade/getTradeVerify?id="+tradeId;
	}
}
