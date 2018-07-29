package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.AdminLoginDTO;
import com.loki.server.service.AdminService;
import com.loki.server.utils.IpUtil;
import com.loki.server.utils.MD5;
import com.loki.server.utils.ResultCodeEnums;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
	@Autowired AdminService adminService;
	
	//后台登录方法
	@RequestMapping(value="/adminLogin",method=RequestMethod.POST)
	@ResponseBody
	public String adminLogin(HttpServletRequest request,HttpSession httpSession,String userName,String password) {
		try {
			if(userName!=null&&password!=null) {
				String md5_password=MD5.getMD5Str(password);
				String ip=IpUtil.getIpFromRequest(request);
				String contextPath=request.getContextPath();
				AdminLoginDTO adminDTO=adminService.login(userName, md5_password,ip,contextPath);
				if(adminDTO != null) {
					//登录成功，保存登录信息
					httpSession.setAttribute("adminId", adminDTO.getAdminDTO().getId());
					httpSession.setAttribute("userName", adminDTO.getAdminDTO().getUserName());
					httpSession.setAttribute("superAdmin", adminDTO.getAdminDTO().isSuperAdmin());
					httpSession.setAttribute("clientAdmin", adminDTO.getAdminDTO());
					httpSession.setAttribute("menuList", adminDTO.getMenuList());
					httpSession.setAttribute("permissionList", adminDTO.getPermissionList());
					httpSession.setAttribute("loginIp", ip);
					
					return responseSuccess();
				}else {
					//登录失败
					return responseFail(ResultCodeEnums.LOGIN_FAIL.getMessage());
				}		
			}else {
				//登录失败
				return responseFail(ResultCodeEnums.PARAM_ERROR.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
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
