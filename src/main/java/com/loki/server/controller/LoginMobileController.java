package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.complexModel.ServiceReturnModel;
import com.loki.server.service.UserLoginService;
import com.loki.server.utils.IpUtil;

@Controller
@RequestMapping("/loginMobile")
public class LoginMobileController {
	@Autowired UserLoginService userService;
	
	//使用用户名密码登录
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request,String userName,String password,String clientType,ModelMap mm) {
		String clientIp=IpUtil.getIpFromRequest(request);
		ServiceReturnModel serviceReturnValue=userService.loginCheck(userName, password,clientIp,clientType);
		if (serviceReturnValue!=null) {
			mm.addAttribute("resultCode", serviceReturnValue.getResultCode());
			mm.addAttribute("msg", serviceReturnValue.getMsg());
			mm.addAttribute("resultObj", serviceReturnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", "-3");
			mm.addAttribute("msg", "未知错误");
		}
		return "mobileResultJson";
	}
	
	//使用令牌登录
	@RequestMapping(value="/userLoginByToken",method=RequestMethod.POST)
	public String userLoginByToken(String userToken,ModelMap mm) {
		ServiceReturnModel serviceReturnValue=userService.loginCheckByToken(userToken);
		if (serviceReturnValue!=null) {
			mm.addAttribute("resultCode", serviceReturnValue.getResultCode());
			mm.addAttribute("msg", serviceReturnValue.getMsg());
			mm.addAttribute("resultObj", serviceReturnValue.getResultObj());
		}
		else {
			mm.addAttribute("resultCode", "-3");
			mm.addAttribute("msg", "未知错误");
		}
		return "mobileResultJson";
	}
	
	//用户注册
	@RequestMapping(value="/userRegist",method=RequestMethod.POST)
	public String userLoginByToken(HttpServletRequest request,String phone,String password,String clientType,ModelMap mm) {
		String clientIp=IpUtil.getIpFromRequest(request);
		ServiceReturnModel serviceReturnValue=userService.regist(phone, password, clientIp, clientType);
		if (serviceReturnValue!=null) {
			mm.addAttribute("resultCode", serviceReturnValue.getResultCode());
			mm.addAttribute("msg", serviceReturnValue.getMsg());
			mm.addAttribute("resultObj", serviceReturnValue.getResultObj());
		}
		else {
			mm.addAttribute("resultCode", "-3");
			mm.addAttribute("msg", "未知错误");
		}
		return "mobileResultJson";
	}
}
