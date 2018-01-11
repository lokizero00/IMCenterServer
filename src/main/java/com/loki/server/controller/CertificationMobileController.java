package com.loki.server.controller;



import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.service.EnterpriseCertificationService;
import com.loki.server.service.IdentityCertificationService;

@Controller
@RequestMapping("/s/api/certification")
public class CertificationMobileController {
	@Autowired IdentityCertificationService identityCertificationService;
	@Autowired EnterpriseCertificationService enterpriseCertificationService;
	
	//更新实名认证
	@RequestMapping(value="/updateIdentityCertification",method=RequestMethod.POST)
	public String updateIdentityCertification(HttpServletRequest request,int userId,String trueName,String identityNumber,String identityFront,String identityBack,ModelMap mm) {
		HashMap<String,Object> returnValue=identityCertificationService.updateIdentityCertification(userId, trueName, identityNumber, identityFront, identityBack);
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
	
	//更新企业认证
	@RequestMapping(value="/updateEnterpriseCertification",method=RequestMethod.POST)
	public String updateEnterpriseCertification(HttpServletRequest request,int userId,String position,String enterpriseName,String licensePic,ModelMap mm) {
		HashMap<String,Object> returnValue=enterpriseCertificationService.updateEnterpriseCertification(userId, position, enterpriseName, licensePic);
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
	
	//获取实名认证
	@RequestMapping(value="/getIdentityCertification",method=RequestMethod.GET)
	public String getIdentityCertification(HttpServletRequest request,int userId,ModelMap mm) {
		HashMap<String,Object> returnValue=identityCertificationService.getIdentityCertification(userId);
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
	
	//获取企业认证
	@RequestMapping(value="/getEnterpriseCertification",method=RequestMethod.GET)
	public String getEnterpriseCertification(HttpServletRequest request,int userId,ModelMap mm) {
		HashMap<String,Object> returnValue=enterpriseCertificationService.getEnterpriseCertification(userId);
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
	
	//获取实名认证状态
	@RequestMapping(value="/getIdentityCertificationStatus",method=RequestMethod.GET)
	public String getIdentityCertificationStatus(HttpServletRequest request,int userId,ModelMap mm) {
		HashMap<String,Object> returnValue=identityCertificationService.getIdentityCertificationStatus(userId);
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
