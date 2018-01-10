package com.loki.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.model.Admin;
import com.loki.server.service.AdminService;
import com.loki.server.utils.MD5;

@Controller
@RequestMapping("/s/admin")
public class AdminController {
	@Autowired AdminService adminService;
	
	/**
	 * 获取所有管理员
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getAllAdmin")
	public String getAllUser(HttpServletRequest request) {
		List<Admin> findAll=adminService.findAll();
		request.setAttribute("adminList", findAll);
		return "/allAdmin";
	}
	
	/**
	 * 跳转到添加界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddAdmin")
	public String toAddAdmin(HttpServletRequest request){
		return "/addAdmin";
	}
	
	/**
	 * 添加并重定向
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addAdmin",method=RequestMethod.POST)
	public String addAdmin(Admin admin,HttpServletRequest request){
		Admin clientAdmin=(Admin) request.getSession().getAttribute("clientAdmin");
		admin.setAdminCreatorId(clientAdmin.getId());
		//md5加密
		admin.setPassword(MD5.getMD5Str(admin.getPassword()));
		adminService.insert(admin);
		return "redirect:/s/admin/getAllAdmin";
	}
	
	/**
	 * 根据id查询单个管理员
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAdmin")
	public String getAdmin(int id,HttpServletRequest request){
		
		request.setAttribute("admin", adminService.findById(id));
		return "/editAdmin";
	}
	
	/**
	 *编辑管理员
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateAdmin")
	public String updateAdmin(Admin admin,HttpServletRequest request){
		Admin clientAdmin=(Admin) request.getSession().getAttribute("clientAdmin");
		admin.setAdminUpdaterId(clientAdmin.getId());
		if(adminService.update(admin)){
			admin = adminService.findById(admin.getId());
			request.setAttribute("admin", admin);
			return "redirect:/s/admin/getAllAdmin";
		}else{
			return "/error";
		}
	}
	
	/**
	 * 删除管理员
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delAdmin")
	public void delAdmin(int id,HttpServletRequest request,HttpServletResponse response){
		String result = "{\"result\":\"error\"}";
		
		if(adminService.delete(id)){
			result = "{\"result\":\"success\"}";
		}
		
		response.setContentType("application/json");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
