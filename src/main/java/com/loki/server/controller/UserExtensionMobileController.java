package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.entity.UserExtension;
import com.loki.server.service.UserExtensionService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/userExtension")
public class UserExtensionMobileController {
	@Autowired UserExtensionService userExtensionService;
	
	@RequestMapping(value="/getUserExtension",method=RequestMethod.GET)
	public String getAlipaySign(HttpServletRequest request, Integer userId,ModelMap mm) {
		ServiceResult<UserExtension> returnValue=userExtensionService.getUserExtension(userId);
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
