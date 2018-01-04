package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.model.EnterpriseCertification;
import com.loki.server.model.IdentityCertification;
import com.loki.server.service.PersonalCenterService;

@Controller
@RequestMapping("/api/personalCenter")
public class PersonalCenterMobileController {
	@Autowired PersonalCenterService personalCenterService;
	
	//个人中心-首页
	@RequestMapping(value="/getPersonalData",method=RequestMethod.GET)
	public String getPersonalData(HttpServletRequest request,int userId,ModelMap mm) {
		HashMap<String,Object> returnValue=personalCenterService.getPersonalCenter(userId);
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
	
	//获取用户信息
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public String getUser(HttpServletRequest request,int userId,ModelMap mm) {
		HashMap<String,Object> returnValue=personalCenterService.getUser(userId);
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
	
	//新增实名认证
	@RequestMapping(value="/updateIdentityCertification",method=RequestMethod.POST)
	public String updateIdentityCertification(HttpServletRequest request,IdentityCertification identityCertification,ModelMap mm) {
		HashMap<String,Object> returnValue=personalCenterService.updateIdentityCertification(identityCertification);
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
	
	//新增企业认证
	@RequestMapping(value="/updateEnterpriseCertification",method=RequestMethod.POST)
	public String updateEnterpriseCertification(HttpServletRequest request,EnterpriseCertification enterpriseCertification,ModelMap mm) {
		HashMap<String,Object> returnValue=personalCenterService.updateEnterpriseCertification(enterpriseCertification);
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
}
