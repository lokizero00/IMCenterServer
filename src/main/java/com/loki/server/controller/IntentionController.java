package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.IntentionDTO;
import com.loki.server.dto.IntentionLogDTO;
import com.loki.server.entity.IntentionJournal;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.IntentionService;
import com.loki.server.service.PayService;
import com.loki.server.utils.ResultCodeEnums;

@Controller
@RequestMapping("/s/intention")
public class IntentionController extends BaseController{
	@Autowired IntentionService intentionService;
	@Autowired 
	PayService payService;

	/**
     * 显示意向金详情页
     * @return
     */
	@RequestMapping("/intentionDetailPage")  
	public String intentionDetailPage(int id){
		return "intention/intentionDetail.jsp?id="+id;
	}
	
	/**
     * 获取意向金详情
     * @return
     */
	@RequestMapping(value="/intentionDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getIntention(HttpServletRequest request, int id) {
		try {
			IntentionDTO intentionDTO=intentionService.getIntention(id);
			return responseSuccess(intentionDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 获取意向金日志
     * @return
     */
	@RequestMapping(value="/intentionLog.do",method=RequestMethod.GET)
	@ResponseBody
	public String getIntentionLog(HttpServletRequest request, int intentionId,String relationType,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("intentionId", intentionId);
			map.put("relationType", relationType);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<IntentionLogDTO> result=intentionService.getIntentionLog(map);
			return responseSuccess(result);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 获取意向金日志(新)
     * @return
     */
	@RequestMapping(value="/intentionJournal.do",method=RequestMethod.GET)
	@ResponseBody
	public String getIntentionJournal(HttpServletRequest request, String type,Integer intentionId,Integer userId,String innerBusiNo,String state,String checkState,String thirdChannel,String isReturn,String outRequestNo,Integer pageNo,Integer pageSize,String sortName,String sortOrder) {
		try {
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
			
			PagedResult<IntentionJournal> returnValue = payService.getIntentionJournal(map);
			return responseSuccess(returnValue);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	
	/**
     * 获取用户意向金详情
     * @return
     */
	@RequestMapping(value="/userIntention.do",method=RequestMethod.GET)
	@ResponseBody
	public String getUserIntention(HttpServletRequest request, int userId) {
		try {
			IntentionDTO intentionDTO=intentionService.getUserIntention(userId);
			return responseSuccess(intentionDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示意向金列表页
     * @return
     */
	@RequestMapping("/intentionListPage")  
	public String intentionListPage(){
		return "intention/intentionList";
	}
	
	/**
     * 获取意向金列表
     * @return
     */
	@RequestMapping(value="/intentionList.do",method=RequestMethod.GET)
	@ResponseBody
	public String getIntentionList(HttpServletRequest request, String phone,String sortName,String sortOrder,Integer pageNo,Integer pageSize) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("phone", phone);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<IntentionDTO> intentionDTOList=intentionService.getIntentionList(map);
			return responseSuccess(intentionDTOList);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 意向金编辑页
     * @return
     */
	@RequestMapping("/intentionEditPage")  
	public String advEditPage(int id){
		return "intention/intentionEdit.jsp?id="+id;
	}
	
	@RequestMapping(value="/intentionEdit.do",method=RequestMethod.POST)
	@ResponseBody
	public String intentionEdit(HttpServletRequest request,IntentionDTO intentionDto) {
		try {
			boolean result=intentionService.editIntention(intentionDto);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.UPDATE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
