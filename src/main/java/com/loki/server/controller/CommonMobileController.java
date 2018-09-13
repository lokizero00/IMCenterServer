package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.UserHideInfoDTO;
import com.loki.server.entity.TradeComplex;
import com.loki.server.service.PersonalCenterService;
import com.loki.server.service.TradeService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.PersonalCenterVO;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/api/common")
public class CommonMobileController extends BaseController{
	@Autowired TradeService tradeService;
	@Autowired PersonalCenterService personalCenterService;
	
	//获取单个贸易,游客身份
	@RequestMapping(value="/showTrade",method=RequestMethod.GET)
	@ResponseBody
	public String getTrade(HttpServletRequest request, Integer tradeId) {
		try {
			ServiceResult<TradeComplex> returnValue=tradeService.getTrade_mobile(tradeId,0);
			return responseSuccess(returnValue);
		}catch(Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
	
	//个人信息
	@RequestMapping(value="/getPersonalData",method=RequestMethod.GET)
	@ResponseBody
	public String getPersonalData(HttpServletRequest request,int userId,ModelMap mm) {
		try {
			ServiceResult<UserHideInfoDTO> returnValue=personalCenterService.getUserHideInfo(userId);
			return responseSuccess(returnValue);
		}catch(Exception e) {
			e.printStackTrace();
			return responseFail(e.getMessage());
		}
	}
}
