package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.service.PersonalCenterService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.PersonalCenterVO;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/personalCenter")
public class PersonalCenterMobileController {
	@Autowired PersonalCenterService personalCenterService;
	
	//个人中心-首页
	@RequestMapping(value="/getPersonalData",method=RequestMethod.GET)
	public String getPersonalData(HttpServletRequest request,int userId,ModelMap mm) {
		ServiceResult<PersonalCenterVO> returnValue=personalCenterService.getPersonalCenter(userId);
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
