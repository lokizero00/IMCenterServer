package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.service.UserBindCodeService;
import com.loki.server.service.UserLoginService;
import com.loki.server.utils.IpUtil;

@Controller
@RequestMapping("/loginMobile")
public class LoginMobileController {
	@Autowired UserLoginService userService;
	@Autowired UserBindCodeService userBindCodeService;
	
	//使用用户名密码登录
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request,String userName,String password,String clientType,ModelMap mm) {
		String clientIp=IpUtil.getIpFromRequest(request);
		HashMap<String,Object> returnValue=userService.loginCheck(userName, password,clientIp,clientType);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.get("resultCode"));
			mm.addAttribute("msg", returnValue.get("msg"));
			mm.addAttribute("resultObj", returnValue.get("resultObj"));
		}else {
			mm.addAttribute("resultCode", "-3");
			mm.addAttribute("msg", "未知错误");
		}
		return "mobileResultJson";
	}
	
	//使用令牌登录
	@RequestMapping(value="/userLoginByToken",method=RequestMethod.POST)
	public String userLoginByToken(String token,ModelMap mm) {
		HashMap<String,Object> returnValue=userService.loginCheckByToken(token);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.get("resultCode"));
			mm.addAttribute("msg", returnValue.get("msg"));
			mm.addAttribute("resultObj", returnValue.get("resultObj"));
		}
		else {
			mm.addAttribute("resultCode", "-3");
			mm.addAttribute("msg", "未知错误");
		}
		return "mobileResultJson";
	}
	
	//用户注册
	@RequestMapping(value="/userRegist",method=RequestMethod.POST)
	public String userLoginByToken(HttpServletRequest request,String phone,String password,String authCode,int authCodeId,String clientType,ModelMap mm) {
		String clientIp=IpUtil.getIpFromRequest(request);
		HashMap<String,Object> returnValue=userService.regist(phone, password, authCode, authCodeId, clientIp, clientType);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.get("resultCode"));
			mm.addAttribute("msg", returnValue.get("msg"));
			mm.addAttribute("resultObj", returnValue.get("resultObj"));
		}
		else {
			mm.addAttribute("resultCode", "-3");
			mm.addAttribute("msg", "未知错误");
		}
		return "mobileResultJson";
	}
	
	//发送短信验证码
	@RequestMapping(value="/sendSmsAuthCode",method=RequestMethod.POST)
	public String sendSmsAuthCode(HttpServletRequest request,String phone,ModelMap mm) {
		HashMap<String,Object> returnValue=userBindCodeService.sendSmsAuthCode(phone);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.get("resultCode"));
			mm.addAttribute("msg", returnValue.get("msg"));
			mm.addAttribute("resultObj", returnValue.get("resultObj"));
		}
		else {
			mm.addAttribute("resultCode", "-3");
			mm.addAttribute("msg", "未知错误");
		}
		return "mobileResultJson";
	}
}
