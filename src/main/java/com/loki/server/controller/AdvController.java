package com.loki.server.controller;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.AdvDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.AdvService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.AdvVO;

@Controller
@RequestMapping("/s/adv")
public class AdvController extends BaseController{
	@Autowired AdvService advService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/advListPage")  
	public String advListPage(){
		return "adv/advList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/advList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getAdvList(Integer adminCreatorId,Integer adminUpdaterId,String position,String title,Integer linkable,Integer state,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("adminCreatorId", adminCreatorId);
			map.put("adminUpdaterId", adminUpdaterId);
			map.put("position", position);
			map.put("title", title);
			map.put("linkable", linkable);
			map.put("state", state);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<AdvDTO> list=advService.getAdvList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示添加页面
     * @return
     */
	@RequestMapping("/advAddPage")  
	public String advAddPage(){
		return "adv/advAdd";
	}
	
	/**
     * 保存新建的广告
     * @return
     */
	@RequestMapping(value="/advAdd.do",method=RequestMethod.POST)
	@ResponseBody
	public String advAdd(HttpServletRequest request, AdvVO advVO) {
		try {
			advVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
			advVO.setAdminCreatorId((int) request.getSession().getAttribute("adminId"));
			boolean result=advService.addAdv(advVO);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.SAVE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示详情页
     * @return
     */
	@RequestMapping("/advDetailPage")  
	public String advDetailPage(int id){
		return "adv/advDetail.jsp?id="+id;
	}
	
	/**
     * 获取广告
     * @return
     */
	@RequestMapping(value="/advDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getAdv(HttpServletRequest request, int id) {
		try {
			AdvDTO advDTO=advService.getAdv(id);
			return responseSuccess(advDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示编辑页
     * @return
     */
	@RequestMapping("/advEditPage")  
	public String advEditPage(int id){
		return "adv/advEdit.jsp?id="+id;
	}
	
	/**
     * 编辑广告
     * @return
     */
	@RequestMapping(value="/advEdit.do",method=RequestMethod.POST)
	@ResponseBody
	public String advEdit(HttpServletRequest request, AdvVO advVO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			advVO.setAdminUpdaterId(adminId);
			advVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean result=advService.editAdv(advVO);
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
     * 删除广告
     * @return
     */
	@RequestMapping(value="/advDel.do",method=RequestMethod.POST)
	@ResponseBody
	public String advDel(HttpServletRequest request, int id) {
		try {
			boolean result=advService.delAdv(id);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.DELETE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
