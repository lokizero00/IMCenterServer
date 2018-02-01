package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loki.server.entity.Admin;
import com.loki.server.service.AdminService;
import com.loki.server.utils.IpUtil;
import com.loki.server.utils.MD5;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired AdminService adminService;
	
	//后台登录方法
	@RequestMapping("/adminLogin")
	public String adminLogin(HttpServletRequest request,HttpSession httpSession,String userName,String password) {
		if(userName!=null&&password!=null) {
			String md5_password=MD5.getMD5Str(password);
			String ip=IpUtil.getIpFromRequest(request);
			Admin admin=adminService.login(userName, md5_password,ip);
			if(admin != null) {
				//登录成功，保存登录信息
				httpSession.setAttribute("adminId", admin.getId());
				httpSession.setAttribute("userName", admin.getUserName());
				httpSession.setAttribute("superAdmin", admin.isSuperAdmin());
				httpSession.setAttribute("clientAdmin", admin);
				
				//保存资源信息
				
				return "redirect:/s/trade/";
			}else {
				//登录失败
				return "redirect:/login.jsp";
			}		
		}else {
			//登录失败
			return "redirect:/login.jsp";
		}
	}
	//后台注销方法
	@RequestMapping("/adminLoginOut")
	public String adminLoginOut(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/login.jsp";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "redirect:/login.jsp";
	}
}
