package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.service.WeiXinAndAliService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/auth")
public class AuthorizationMobileController {
	@Autowired WeiXinAndAliService weiXinAndAliService;
	
	@RequestMapping(value="/getAlipaySign",method=RequestMethod.GET)
	public String getAlipaySign(HttpServletRequest request,ModelMap mm) {
		ServiceResult<String> returnValue=weiXinAndAliService.awakenAliAuthInfoStr();
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
	
	@RequestMapping(value="/bindAlipayAccount",method=RequestMethod.POST)
	public String bindAlipayAccount(HttpServletRequest request,String authCode,Integer userId,ModelMap mm) {
		ServiceResult<Void> returnValue=weiXinAndAliService.getAlipayAccount(authCode, userId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
			
		return "mobileResultJson";
	}
	
	@RequestMapping(value="/bindWxOpenId",method=RequestMethod.POST)
	public String bindWxOpenId(HttpServletRequest request,String code,Integer userId,ModelMap mm) {
		ServiceResult<Void> returnValue=weiXinAndAliService.getWxOpenid(code,userId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
}
