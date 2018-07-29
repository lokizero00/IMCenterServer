package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.AdminDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.AdminService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.AdminVO;

@Controller
@RequestMapping("/s/admin")
public class AdminController extends BaseController{
	@Autowired AdminService adminService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/adminListPage")  
	public String adminListPage(){
		return "admin/adminList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/adminList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getAdminList(Integer adminCreatorId,Integer adminUpdaterId,String userName,boolean superAdmin,String status,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("adminCreatorId", adminCreatorId);
			map.put("adminUpdaterId", adminUpdaterId);
			map.put("userName", userName);
			map.put("superAdmin", superAdmin);
			map.put("status", status);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<AdminDTO> list=adminService.getAdminList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
	
    /**
     * 显示添加页面
     * @return
     */
	@RequestMapping("/advAdminPage")  
	public String advAdminPage(){
		return "admin/adminAdd";
	}
	
	/**
     * 保存管理员
     * @return
     */
	@RequestMapping(value="/adminAdd.do",method=RequestMethod.POST)
	@ResponseBody
	public String adminAdd(HttpServletRequest request, AdminVO adminVO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			adminVO.setAdminCreatorId(adminId);
			boolean result=adminService.add(adminVO);
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
	@RequestMapping("/adminDetailPage")  
	public String adminDetailPage(int id){
		return "admin/adminDetail.jsp?id="+id;
	}
	
	/**
     * 获取管理员
     * @return
     */
	@RequestMapping(value="/adminDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String adminDetail(HttpServletRequest request, Integer id) {
		try {
			AdminDTO adminDTO=adminService.getAdmin(id);
			return responseSuccess(adminDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示编辑页
     * @return
     */
	@RequestMapping("/adminEditPage")  
	public String adminEditPage(int id){
		return "admin/adminEdit.jsp?id="+id;
	}
	
	/**
     * 编辑管理员
     * @return
     */
	@RequestMapping(value="/adminEdit.do",method=RequestMethod.POST)
	@ResponseBody
	public String adminEdit(HttpServletRequest request, AdminVO adminVO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			adminVO.setAdminUpdaterId(adminId);
			boolean result=adminService.edit(adminVO);
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
     * 删除管理员
     * @return
     */
	@RequestMapping(value="/adminDel.do",method=RequestMethod.POST)
	@ResponseBody
	public String adminDel(HttpServletRequest request, Integer id) {
		try {
			boolean result=adminService.delete(id);
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
