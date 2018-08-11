package com.loki.server.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.ResourceTreeDto;
import com.loki.server.dto.ResourcesDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.ResourcesService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ResourcesVO;

@Controller
@RequestMapping("/s/resources")
public class ResourcesController extends BaseController{
	@Autowired ResourcesService resourcesService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/resourcesListPage")  
	public String resourcesListPage(){
		return "resources/resourcesList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/resourcesList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getResourcesList(String name,String type,String status,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("name", name);
			map.put("type", type);
			map.put("status", status);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",100);
			List<ResourcesDTO> list=resourcesService.getResourcesList(map);
			HashMap<String,Object> map1=new HashMap<>();
			map1.put("rows", list);
			map1.put("total", list.size());
    	    return responseSuccess(map1);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示添加页面
     * @return
     */
	@RequestMapping("/resourcesAddPage")  
	public String resourcesAddPage(){
		return "resources/resourcesAdd";
	}
	
	/**
     * 保存新建的资源
     * @return
     */
	@RequestMapping(value="/resourcesAdd.do",method=RequestMethod.POST)
	@ResponseBody
	public String resourcesAdd(HttpServletRequest request, ResourcesVO resourcesVO) {
		try {
			resourcesVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
			resourcesVO.setAdminCreatorId((int) request.getSession().getAttribute("adminId"));
			boolean result=resourcesService.addResources(resourcesVO);
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
	@RequestMapping("/resourcesDetailPage")  
	public String resourcesDetailPage(int id){
		return "resources/resourcesDetail.jsp?id="+id;
	}
	
	/**
     * 获取资源
     * @return
     */
	@RequestMapping(value="/resourcesDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getResources(HttpServletRequest request, int id) {
		try {
			ResourcesDTO resourcesDTO=resourcesService.getResources(id);
			return responseSuccess(resourcesDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示编辑页
     * @return
     */
	@RequestMapping("/resourcesEditPage")  
	public String resourcesEditPage(int id){
		return "resources/resourcesEdit.jsp?id="+id;
	}
	
	/**
     * 编辑资源
     * @return
     */
	@RequestMapping(value="/resourcesEdit.do",method=RequestMethod.POST)
	@ResponseBody
	public String resourcesEdit(HttpServletRequest request, ResourcesVO resourcesVO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			resourcesVO.setAdminUpdaterId(adminId);
			resourcesVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean result=resourcesService.editResources(resourcesVO);
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
     * 删除资源
     * @return
     */
	@RequestMapping(value="/resourcesDel.do",method=RequestMethod.POST)
	@ResponseBody
	public String resourcesDel(HttpServletRequest request, int id) {
		try {
			boolean result=resourcesService.delResources(id);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.DELETE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 资源树查询
     * @return
     */
    @RequestMapping(value="/resourcesListTree.do")
    @ResponseBody
    public String getResourcesListTree(int id) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("roleId", id);
//			map.put("type", type);
//			map.put("status", status);
//			map.put("sortName", sortName);
//			map.put("sortOrder", sortOrder);
//			map.put("pageNo",pageNo);
//			map.put("pageSize",pageSize);
			List<ResourceTreeDto> list=resourcesService.getResourcesListTree(map);
    	    return responseArraySuccess(list);
    	} catch (Exception e) {
    		e.printStackTrace();
			return responseFail(e.getMessage());
		}
    }
    
}
