package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.UserBindCode;
import com.loki.server.service.UserBindCodeService;
import com.loki.server.service.UserService;
import com.loki.server.utils.IpUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.UserLoginVO;

@Controller
@RequestMapping("/api/login")
public class LoginMobileController {
	@Autowired UserService userService;
	@Autowired UserBindCodeService userBindCodeService;
	
	//使用用户名密码登录
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request,String phone,String password,String clientType,ModelMap mm) {
		String clientIp=IpUtil.getIpFromRequest(request);
		ServiceResult<UserLoginVO> returnValue=userService.loginCheck(phone, password,clientIp,clientType);
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
	
	//使用令牌登录
	@RequestMapping(value="/userLoginByToken",method=RequestMethod.POST)
	public String userLoginByToken(String token,ModelMap mm) {
		ServiceResult<UserLoginVO> returnValue=userService.loginCheckByToken(token);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}
		else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//用户注册
	@RequestMapping(value="/userRegist",method=RequestMethod.POST)
	public String userLoginByToken(HttpServletRequest request,String phone,String password,String authCode,int authCodeId,String clientType,ModelMap mm) {
		String clientIp=IpUtil.getIpFromRequest(request);
		ServiceResult<UserLoginVO> returnValue=userService.regist(phone, password, authCode, authCodeId, clientIp, clientType);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}
		else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//发送短信验证码
	@RequestMapping(value="/sendSmsAuthCode",method=RequestMethod.POST)
	public String sendSmsAuthCode(HttpServletRequest request,String phone,ModelMap mm) {
		ServiceResult<UserBindCode> returnValue=userBindCodeService.sendSmsAuthCode(phone);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}
		else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//token校验错误
	@RequestMapping(value="/tokenInvalid")
	public String tokenInvalid(HttpServletRequest request,ModelMap mm) {
		mm.addAttribute("resultCode", ResultCodeEnums.TOKEN_INVALID.getCode());
		mm.addAttribute("msg", ResultCodeEnums.TOKEN_INVALID.getMessage());
		return "mobileResultJson";
	}
}
