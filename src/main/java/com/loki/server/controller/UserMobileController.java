package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.entity.User;
import com.loki.server.service.UserBindCodeService;
import com.loki.server.service.UserService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/user")
public class UserMobileController {
	@Autowired UserService userService;
	@Autowired UserBindCodeService userBindCodeService;
	
	//获取用户信息
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public String getUser(HttpServletRequest request,int userId,ModelMap mm) {
		ServiceResult<User> returnValue=userService.getUser(userId);
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
	
	//更新昵称
	@RequestMapping(value="/updateNickName",method=RequestMethod.POST)
	public String updateNickName(HttpServletRequest request,int userId,String nickName,ModelMap mm) {
		ServiceResult<Void> returnValue=userService.updateNickName(userId, nickName);
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
	
	//验证码校验，绑定手机时使用
	@RequestMapping(value="/checkAuthCode",method=RequestMethod.GET)
	public String checkAuthCode(HttpServletRequest request,int authCodeId, String authCode,ModelMap mm) {
		ServiceResult<Void> returnValue=userBindCodeService.checkAuthCode(authCodeId, authCode);
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
	
	//绑定新手机号
	@RequestMapping(value="/rebindPhone",method=RequestMethod.POST)
	public String rebindPhone(HttpServletRequest request,int userId, String phone, String authCode, int authCodeId,ModelMap mm) {
		ServiceResult<Void> returnValue=userService.updatePhone(userId, phone, authCode, authCodeId);
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
