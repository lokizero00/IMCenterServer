package com.loki.server.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.service.EnterpriseCertificationService;
import com.loki.server.service.IdentityCertificationService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.EnterpriseCertificationVO;
import com.loki.server.vo.IdentityCertificationVO;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/certification")
public class CertificationMobileController {
	@Autowired IdentityCertificationService identityCertificationService;
	@Autowired EnterpriseCertificationService enterpriseCertificationService;
	
	//添加实名认证
	@RequestMapping(value="/addIdentityCertification",method=RequestMethod.POST)
	public String addIdentityCertification(HttpServletRequest request,@RequestBody IdentityCertificationVO identityCertificationVO,ModelMap mm) {
		ServiceResult<Integer> returnValue=identityCertificationService.addIdentityCertification(identityCertificationVO);
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
	
	//修改实名认证
	@RequestMapping(value="/editIdentityCertification",method=RequestMethod.POST)
	public String editIdentityCertification(HttpServletRequest request,@RequestBody IdentityCertificationVO identityCertificationVO,ModelMap mm) {
		ServiceResult<Void> returnValue=identityCertificationService.editIdentityCertification(identityCertificationVO);
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
	
	//添加企业认证
	@RequestMapping(value="/addEnterpriseCertification",method=RequestMethod.POST)
	public String addEnterpriseCertification(HttpServletRequest request,@RequestBody EnterpriseCertificationVO enterpriseCertificationVO,ModelMap mm) {
		ServiceResult<Integer> returnValue=enterpriseCertificationService.addEnterpriseCertification(enterpriseCertificationVO);
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
		
	//修改企业认证
	@RequestMapping(value="/editEnterpriseCertification",method=RequestMethod.POST)
	public String editEnterpriseCertification(HttpServletRequest request,@RequestBody EnterpriseCertificationVO enterpriseCertificationVO,ModelMap mm) {
		ServiceResult<Void> returnValue=enterpriseCertificationService.editEnterpriseCertification(enterpriseCertificationVO);
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
