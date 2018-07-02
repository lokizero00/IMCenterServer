package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.service.UserCollectionService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/userCollection")
public class UserCollectionMobileController {
	@Autowired UserCollectionService userCollectionService;
	
	//添加收藏
	@RequestMapping(value="/addCollection",method=RequestMethod.POST)
	public String addCollection(HttpServletRequest request, Integer tradeId,Integer userId,String type,ModelMap mm) {
		ServiceResult<Void> returnValue=userCollectionService.addCollection(userId, tradeId, type);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
				
		return "mobileResultJson";
	}
	
	//取消收藏
	@RequestMapping(value="/delCollection",method=RequestMethod.POST)
	public String delCollection(HttpServletRequest request, Integer collectionId,ModelMap mm) {
		ServiceResult<Void> returnValue=userCollectionService.delCollection(collectionId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
				
		return "mobileResultJson";
	}
}
