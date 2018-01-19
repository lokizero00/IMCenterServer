package com.loki.server.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.service.EnterpriseCertificationService;
import com.loki.server.service.IdentityCertificationService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/certification")
public class CertificationMobileController {
	@Autowired IdentityCertificationService identityCertificationService;
	@Autowired EnterpriseCertificationService enterpriseCertificationService;
	
	//更新实名认证
	@RequestMapping(value="/updateIdentityCertification",method=RequestMethod.POST)
	public String updateIdentityCertification(HttpServletRequest request,int userId,String trueName,String identityNumber,String identityFront,String identityBack,ModelMap mm) {
		ServiceResult<IdentityCertification> returnValue=identityCertificationService.updateIdentityCertification(userId, trueName, identityNumber, identityFront, identityBack);
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
	
	//更新企业认证
	@RequestMapping(value="/updateEnterpriseCertification",method=RequestMethod.POST)
	public String updateEnterpriseCertification(HttpServletRequest request,int userId,String position,String enterpriseName,String licensePic,ModelMap mm) {
		ServiceResult<EnterpriseCertification> returnValue=enterpriseCertificationService.updateEnterpriseCertification(userId, position, enterpriseName, licensePic);
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
	
	//获取实名认证
	@RequestMapping(value="/getIdentityCertification",method=RequestMethod.GET)
	public String getIdentityCertification(HttpServletRequest request,int userId,ModelMap mm) {
		ServiceResult<IdentityCertification> returnValue=identityCertificationService.getIdentityCertification(userId);
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
	
	//获取企业认证
	@RequestMapping(value="/getEnterpriseCertification",method=RequestMethod.GET)
	public String getEnterpriseCertification(HttpServletRequest request,int userId,ModelMap mm) {
		ServiceResult<EnterpriseCertification> returnValue=enterpriseCertificationService.getEnterpriseCertification(userId);
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
	
	//获取实名认证状态
	@RequestMapping(value="/getIdentityCertificationStatus",method=RequestMethod.GET)
	public String getIdentityCertificationStatus(HttpServletRequest request,int userId,ModelMap mm) {
		ServiceResult<String> returnValue=identityCertificationService.getIdentityCertificationStatus(userId);
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
