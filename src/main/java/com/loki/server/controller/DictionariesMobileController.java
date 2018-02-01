package com.loki.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.entity.Dictionaries;
import com.loki.server.service.DictionariesService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/dictionaries")
public class DictionariesMobileController {
	@Autowired DictionariesService dictionariesService;
	//获取系统字典
	@RequestMapping(value="/getDictionariesByType",method=RequestMethod.GET)
	public String getDictionariesByType(HttpServletRequest request,String type,ModelMap mm) {
		ServiceResult<List<Dictionaries>> returnValue=dictionariesService.getDictionariesListMobile(type);
		if(returnValue!=null) {
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
