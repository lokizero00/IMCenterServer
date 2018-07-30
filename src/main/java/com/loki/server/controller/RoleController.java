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

import com.loki.server.dto.RoleDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.RoleResources;
import com.loki.server.service.RoleService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.RoleVO;

@Controller
@RequestMapping("/s/role")
public class RoleController extends BaseController{
	@Autowired RoleService roleService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/roleListPage")  
	public String roleListPage(){
		return "role/roleList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/roleList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getRoleList(String name,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("name", name);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<RoleDTO> list=roleService.getRoleList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示添加页面
     * @return
     */
	@RequestMapping("/roleAddPage")  
	public String roleAddPage(){
		return "role/roleAdd";
	}
	
	/**
     * 保存新建的角色
     * @return
     */
	@RequestMapping(value="/roleAdd.do",method=RequestMethod.POST)
	@ResponseBody
	public String roleAdd(HttpServletRequest request, RoleVO roleVO) {
		try {
			roleVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
			roleVO.setAdminCreatorId((int) request.getSession().getAttribute("adminId"));
			boolean result=roleService.addRole(roleVO);
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
	@RequestMapping("/roleDetailPage")  
	public String roleDetailPage(int id){
		return "role/roleDetail.jsp?id="+id;
	}
	
	/**
     * 获取角色
     * @return
     */
	@RequestMapping(value="/roleDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getRole(HttpServletRequest request, int id) {
		try {
			RoleDTO roleDTO=roleService.getRole(id);
			return responseSuccess(roleDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示编辑页
     * @return
     */
	@RequestMapping("/roleEditPage")  
	public String roleEditPage(int id){
		return "role/roleEdit.jsp?id="+id;
	}
	
	/**
     * 编辑角色
     * @return
     */
	@RequestMapping(value="/roleEdit.do",method=RequestMethod.POST)
	@ResponseBody
	public String roleEdit(HttpServletRequest request, RoleVO roleVO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			roleVO.setAdminUpdaterId(adminId);
			roleVO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean result=roleService.editRole(roleVO);
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
     * 删除角色
     * @return
     */
	@RequestMapping(value="/roleDel.do",method=RequestMethod.POST)
	@ResponseBody
	public String roleDel(HttpServletRequest request, int id) {
		try {
			boolean result=roleService.delRole(id);
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
     * 通过角色获取资源
     * @return
     */
	@RequestMapping(value="/roleResource.do",method=RequestMethod.POST)
	@ResponseBody
	public String roleResource(HttpServletRequest request, Integer roleId) {
		try {
			List<RoleResources> result=roleService.getResources(roleId);
			return responseArraySuccess(result);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 角色授权资源
     * @return
     */
	@RequestMapping(value="/authRole.do",method=RequestMethod.POST)
	@ResponseBody
	public String authRole(HttpServletRequest request, String authJson) {
		try {
			boolean result=roleService.authRole(authJson);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.AUTH_RESOURCE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
