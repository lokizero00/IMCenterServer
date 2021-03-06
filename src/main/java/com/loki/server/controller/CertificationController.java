package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.EnterpriseCertificationDTO;
import com.loki.server.dto.IdentityCertificationDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.EnterpriseCertificationService;
import com.loki.server.service.IdentityCertificationService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.ResultCodeEnums;

@Controller
@RequestMapping("/s/certification")
public class CertificationController extends BaseController{
	@Autowired IdentityCertificationService identityCertificationService;
	@Autowired EnterpriseCertificationService enterpriseCertificationService;
	
	
	/**
     * 显示实名认证首页
     * @return
     */
	@RequestMapping("/identityCertificationListPage")  
	public String identityCertificationListPage(){
		return "certification/identityCertificationList";
	}
	
	/**
     * 实名认证分页查询
     * @return
     */
    @RequestMapping(value="/identityCertificationList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getIdentityCertificationList(String createTimeStart_str,String createTimeEnd_str,String status,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("createTimeStart", CommonUtil.getInstance().getDate(createTimeStart_str, "start"));
			map.put("createTimeEnd", CommonUtil.getInstance().getDate(createTimeEnd_str, "end"));
			map.put("updateTimeStart", null);
			map.put("updateTimeEnd", null);
			map.put("status", status);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<IdentityCertificationDTO> list=identityCertificationService.getIdentityCertificationList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示实名认证详情页
     * @return
     */
	@RequestMapping("/identityCertificationDetailPage")  
	public String identityCertificationDetailPage(int id){
		return "certification/identityCertificationDetail.jsp?id="+id;
	}
	
	/**
     * 获取实名认证
     * @return
     */
	@RequestMapping(value="/identityCertificationDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getIdentityCertification(HttpServletRequest request, int id) {
		try {
			IdentityCertificationDTO identityCertificationDTO=identityCertificationService.getIdentityCertification(request, id);
			return responseSuccess(identityCertificationDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 实名认证审核
     * @return
     */
	@RequestMapping(value="/verifyIdentityCertification.do",method=RequestMethod.POST)
	@ResponseBody
	public String verifyIdentityCertification(HttpServletRequest request, int id,String verifyResult,String refuseReason) {
		try {
			boolean result=identityCertificationService.verifyIdentityCertification(request, id, verifyResult, refuseReason);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.UPDATE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示企业认证首页
     * @return
     */
	@RequestMapping("/enterpriseCertificationListPage")  
	public String enterpriseCertificationListPage(){
		return "certification/enterpriseCertificationList";
	}
	
	/**
     * 企业认证分页查询
     * @return
     */
    @RequestMapping(value="/enterpriseCertificationList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getEnterpriseCertificationList(String createTimeStart_str,String createTimeEnd_str,String status,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("createTimeStart", CommonUtil.getInstance().getDate(createTimeStart_str, "start"));
			map.put("createTimeEnd", CommonUtil.getInstance().getDate(createTimeEnd_str, "end"));
			map.put("updateTimeStart", null);
			map.put("updateTimeEnd", null);
			map.put("status", status);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<EnterpriseCertificationDTO> list=enterpriseCertificationService.getEnterpriseCertificationList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示企业认证详情页
     * @return
     */
	@RequestMapping("/enterpriseCertificationDetailPage")  
	public String enterpriseCertificationDetailPage(int id){
		return "certification/enterpriseCertificationDetail.jsp?id="+id;
	}
	
	/**
     * 获取企业认证
     * @return
     */
	@RequestMapping(value="/enterpriseCertificationDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getEnterpriseCertification(HttpServletRequest request, int id) {
		try {
			EnterpriseCertificationDTO enterpriseCertificationDTO=enterpriseCertificationService.getEnterpriseCertification(request, id);
			return responseSuccess(enterpriseCertificationDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 企业认证审核
     * @return
     */
	@RequestMapping(value="/verifyEnterpriseCertification.do",method=RequestMethod.POST)
	@ResponseBody
	public String verifyEnterpriseCertification(HttpServletRequest request, int id,String verifyResult,String refuseReason) {
		try {
			boolean result=enterpriseCertificationService.verifyEnterpriseCertification(request, id, verifyResult, refuseReason);
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
