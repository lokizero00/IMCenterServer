package com.loki.server.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.dto.IntentionRechargeDTO;
import com.loki.server.dto.RechargeDTO;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionJournal;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserBankcard;
import com.loki.server.service.IntentionService;
import com.loki.server.service.PayService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/intention")
public class IntentionMobileController {
	@Autowired
	IntentionService intentionService;
	@Autowired 
	PayService payService;

	// 获取意向金账户
	@RequestMapping(value = "/getIntention", method = RequestMethod.GET)
	public String getIntention(HttpServletRequest request, int userId, ModelMap mm) {
		ServiceResult<Intention> returnValue = intentionService.getIntention_mobile(userId);
		if (returnValue != null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		} else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}

	// 获取意向金明细
	@RequestMapping(value = "/getIntentionLog", method = RequestMethod.GET)
	public String getIntentionLog(HttpServletRequest request, int intentionId, String relationType, Integer pageNo,
			Integer pageSize, ModelMap mm) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("intentionId", intentionId);
		map.put("relationType", relationType);
		ServiceResult<PagedResult<IntentionLog>> returnValue = intentionService.getIntentionLog_mobile(map, pageNo,
				pageSize);
		if (returnValue != null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		} else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}

	// 获取用户银行卡列表
	@RequestMapping(value = "/getUserBankcardList", method = RequestMethod.GET)
	public String getUserBankcardList(HttpServletRequest request, int userId, ModelMap mm) {
		ServiceResult<List<UserBankcard>> returnValue = intentionService.getUserBankcard_mobile(userId);
		if (returnValue != null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		} else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}

	// 添加银行卡
	@RequestMapping(value = "/addUserBankcard", method = RequestMethod.POST)
	public String addUserBankcard(HttpServletRequest request, UserBankcard userBankcard, ModelMap mm) {
		ServiceResult<Void> returnValue = intentionService.addUserBankcard_mobile(userBankcard);
		if (returnValue != null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		} else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}

	// 删除银行卡
	@RequestMapping(value = "/deleteUserBankcard", method = RequestMethod.GET)
	public String deleteUserBankcard(HttpServletRequest request, int userBankcardId, ModelMap mm) {
		ServiceResult<Void> returnValue = intentionService.deleteUserBankcard_mobile(userBankcardId);
		if (returnValue != null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		} else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}

	// 预存款金充值
	@RequestMapping(value = "/intentionRecharge", method = RequestMethod.POST)
	public String intentionRecharge(HttpServletRequest request,Integer payType,BigDecimal amount,Integer userId,ModelMap mm) {
		String ip = request.getHeader("X-Forwarded-For");
//		if (StringUtils.isBlank(ip)) {
			ip = "127.0.0.1";
//		}
		IntentionRechargeDTO intentionRechargeDTO=new IntentionRechargeDTO();
		intentionRechargeDTO.setUserId(userId);
		intentionRechargeDTO.setPayType(payType);
		intentionRechargeDTO.setRechargeAmount(amount);
		ServiceResult<RechargeDTO> returnValue = payService.intentionRecharge(intentionRechargeDTO, ip);
		if (returnValue != null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		} else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	// 获取充值/提现流水
	@RequestMapping(value = "/getIntentionJournal", method = RequestMethod.GET)
	public String getIntentionJournal(HttpServletRequest request,String type,Integer intentionId,Integer userId,String innerBusiNo,String state,String checkState,String thirdChannel,String isReturn,String outRequestNo,Integer pageNo,Integer pageSize,String sortName,String sortOrder,ModelMap mm) {
		HashMap<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("intentionId", intentionId);
		map.put("userId", userId);
		map.put("innerBusiNo", innerBusiNo);
		map.put("state", state);
		map.put("checkState", checkState);
		map.put("thirdChannel", thirdChannel);
		map.put("isReturn", isReturn);
		map.put("outRequestNo", outRequestNo);
		map.put("sortName", sortName);
		map.put("sortOrder", sortOrder);
		map.put("pageNo",pageNo);
		map.put("pageSize",pageSize);
		
		ServiceResult<PagedResult<IntentionJournal>> returnValue = payService.getIntentionJournal(map);
		if (returnValue != null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		} else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}

}
