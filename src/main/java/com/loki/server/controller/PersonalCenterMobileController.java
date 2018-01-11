package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.service.PersonalCenterService;

@Controller
@RequestMapping("/s/api/personalCenter")
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
}
