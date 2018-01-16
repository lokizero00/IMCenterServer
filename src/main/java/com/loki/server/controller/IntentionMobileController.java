package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.dto.PagedResult;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.IntentionLog;
import com.loki.server.service.IntentionService;
import com.loki.server.utils.ResultCodeEnums;

@Controller
@RequestMapping("/s/api/intention")
public class IntentionMobileController {
	@Autowired IntentionService intentionService;

	//获取意向金明细
	@RequestMapping(value="/getIntentionLog",method=RequestMethod.GET)
	public String getIntentionLog(HttpServletRequest request,int userId,int intentionId,int adminId,String type,Integer pageNo, Integer pageSize,ModelMap mm) {
		ServiceResult<PagedResult<IntentionLog>> returnValue=intentionService.getIntentionLog(userId,intentionId, adminId, type,pageNo,pageSize);
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
