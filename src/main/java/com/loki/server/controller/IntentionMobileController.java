package com.loki.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.dto.PagedResult;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.UserBankcard;
import com.loki.server.service.IntentionService;
import com.loki.server.utils.ResultCodeEnums;

@Controller
@RequestMapping("/s/api/intention")
public class IntentionMobileController {
	@Autowired IntentionService intentionService;

	//获取意向金账户
	@RequestMapping(value="/getIntention",method=RequestMethod.GET)
	public String getIntention(HttpServletRequest request,int userId,ModelMap mm) {
		ServiceResult<Intention> returnValue=intentionService.getIntention(userId);
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
	
	//获取意向金明细
	@RequestMapping(value="/getIntentionLog",method=RequestMethod.GET)
	public String getIntentionLog(HttpServletRequest request,int userId,int intentionId,int adminId,String type,Integer pageNo, Integer pageSize,ModelMap mm) {
		ServiceResult<PagedResult<IntentionLog>> returnValue=intentionService.getIntentionLog(userId,intentionId, adminId, type,pageNo,pageSize);
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
	
	//获取用户银行卡列表
	@RequestMapping(value="/getUserBankcardList",method=RequestMethod.GET)
	public String getUserBankcardList(HttpServletRequest request,int userId,ModelMap mm) {
		ServiceResult<List<UserBankcard>> returnValue=intentionService.getUserBankcard(userId);
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
	
	//添加银行卡
	@RequestMapping(value="/addUserBankcard",method=RequestMethod.GET)
	public String addUserBankcard(HttpServletRequest request,UserBankcard userBankcard,ModelMap mm) {
		ServiceResult<UserBankcard> returnValue=intentionService.addUserBankcard(userBankcard);
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
