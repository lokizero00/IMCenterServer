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
import com.loki.server.entity.PagedResult;
import com.loki.server.service.IntentionService;

@Controller
@RequestMapping("/s/intention")
public class IntentionController extends BaseController{
	@Autowired IntentionService intentionService;

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
		return "intention/intentionList.jsp";
	}
	
	/**
     * 获取意向金列表
     * @return
     */
	@RequestMapping(value="/intentionList.do",method=RequestMethod.GET)
	@ResponseBody
	public String getIntentionList(HttpServletRequest request, String phone,String sortName,String sortOrder,String pageNo,String pageSize) {
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
}
