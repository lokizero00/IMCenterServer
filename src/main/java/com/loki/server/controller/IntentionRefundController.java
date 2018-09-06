package com.loki.server.controller;

import java.util.HashMap;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.IntentionRefundDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.IntentionRefundService;
import com.loki.server.service.IntentionService;

@Controller
@RequestMapping("/s/intentionRefund")
public class IntentionRefundController  extends BaseController{
	
	@Autowired IntentionRefundService  intentionRefundservice;
	@Autowired IntentionService intentionService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/intentionRefundListPage")  
	public String intentionRefundListPage(){
		return "intentionRefund/intentionRefundList";
	}
	

	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/intentionRefundList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getIntentionRefundList(String name,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("name", name);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<IntentionRefundDTO> list=intentionRefundservice.getIntentionRefundList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
	
	@ResponseBody
    @RequestMapping(value = "/passIntentionCash.do",method = RequestMethod.POST)
    public String passIntentionCash(HttpServletRequest request,Integer intentionRefundId) {
    		try {
    			int adminId=(int) request.getSession().getAttribute("adminId");
			intentionService.passIntentionCash(intentionRefundId, adminId);
			return responseSuccess();
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
    }
	
	@ResponseBody
    @RequestMapping(value = "/notPassIntentionCash.do",method = RequestMethod.POST)
    public String notPassIntentionCash(HttpServletRequest request,Integer intentionRefundId) {
    		try {
    			int adminId=(int) request.getSession().getAttribute("adminId");
			intentionService.notPassIntentionCash(intentionRefundId, adminId);
			return responseSuccess();
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
    }

}
