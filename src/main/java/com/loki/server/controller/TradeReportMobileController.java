package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.dto.TradeReportDTO;
import com.loki.server.dto.TradeReportInformationDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.TradeReportService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeReportInformationVO;
import com.loki.server.vo.TradeReportVO;

@Controller
@RequestMapping("/s/api/tradeReport")
public class TradeReportMobileController {
	@Autowired
	TradeReportService tradeReportService;

	/**
	 * 发起举报
	 * @param request
	 * @param tradeReportVO
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/addTradeReport", method = RequestMethod.POST)
	public String addTradeReport(HttpServletRequest request, @RequestBody TradeReportVO tradeReportVO, ModelMap mm) {
		ServiceResult<Integer> returnValue=tradeReportService.addTradeReport(tradeReportVO);
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
	
	/**
	 * 补充举报证据（举报/申诉）
	 * @param request
	 * @param tradeReportInformationVO
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/addTradeReportInformation", method = RequestMethod.POST)
	public String addTradeReportInformation(HttpServletRequest request, @RequestBody TradeReportInformationVO tradeReportInformationVO, ModelMap mm) {
		ServiceResult<Integer> returnValue=tradeReportService.addTradeReportInformation(tradeReportInformationVO);
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
	
	/**
	 * 举报发起者取消举报
	 * @param request
	 * @param tradeReportId
	 * @param informerId
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/cancelTradeReport", method = RequestMethod.POST)
	public String cancelTradeReport(HttpServletRequest request, Integer tradeReportId,Integer informerId, ModelMap mm) {
		ServiceResult<Void> returnValue=tradeReportService.cancelTradeReport(tradeReportId, informerId);
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
	
	/**
	 * 获取举报列表
	 * @param request
	 * @param tradeId
	 * @param pageNo
	 * @param pageSize
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/getTradeReportList", method = RequestMethod.GET)
	public String getTradeReportList(HttpServletRequest request, int tradeId,Integer pageNo,Integer pageSize, ModelMap mm) {
		ServiceResult<PagedResult<TradeReportDTO>> returnValue=tradeReportService.getTradeReportList(tradeId, pageNo, pageSize);
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
	
	/**
	 * 获取举报
	 * @param request
	 * @param tradeReportId
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/getTradeReport", method = RequestMethod.GET)
	public String getTradeReport(HttpServletRequest request, int tradeReportId, ModelMap mm) {
		ServiceResult<TradeReportDTO> returnValue=tradeReportService.getTradeReport(tradeReportId);
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
	
	/**
	 * 获取举报证言列表
	 * @param request
	 * @param tradeReportId
	 * @param pageNo
	 * @param pageSize
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/getTradeReportInformationList", method = RequestMethod.GET)
	public String getTradeReportInformationList(HttpServletRequest request, int tradeReportId,Integer pageNo,Integer pageSize, ModelMap mm) {
		ServiceResult<PagedResult<TradeReportInformationDTO>> returnValue=tradeReportService.getTradeReportInformationList(tradeReportId, pageNo, pageSize);
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
