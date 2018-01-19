package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.TradeService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeMobileVO;

@Controller
@RequestMapping("/s/api/trade")
public class TradeMobileController {
	@Autowired TradeService tradeService;
	
	//个人中心-首页
	@RequestMapping(value="/publishTrade",method=RequestMethod.POST)
	public String publishTrade(HttpServletRequest request, @RequestBody TradeMobileVO tradeMobileVO,ModelMap mm) {
//		ServiceResult<PagedResult<IntentionLog>> returnValue=intentionService.getIntentionLog(userId,intentionId, adminId, type,pageNo,pageSize);
//		if (returnValue!=null) {
//			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
//			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
//			mm.addAttribute("resultObj", returnValue.getResultObj());
//		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
//		}
		return "mobileResultJson";
	}
}
