package com.loki.server.controller;



import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.UserDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.UserService;
import com.loki.server.utils.ResultCodeEnums;

@Controller
@RequestMapping("/s/user")
public class UserController extends BaseController{
	@Autowired UserService userService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/userListPage")  
	public String userListPage(){
		return "user/userList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/userList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getUserList(String phone,String status,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("phone", phone);
			map.put("status", status);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<UserDTO> list=userService.getUserList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示详情页
     * @return
     */
	@RequestMapping("/userDetailPage")  
	public String userDetailPage(int id){
		return "user/userDetail.jsp?id="+id;
	}
	
	/**
     * 获取用户
     * @return
     */
	@RequestMapping(value="/userDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getUser(HttpServletRequest request, int id) {
		try {
			UserDTO userDTO=userService.getUser(request,id);
			return responseSuccess(userDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 停用/启用
     * @return
     */
	@RequestMapping(value="/changeUserStatus.do",method=RequestMethod.POST)
	@ResponseBody
	public String changeUserStatus(HttpServletRequest request, int id) {
		try {
			UserDTO userDTO=userService.changeUserStatus(request,id);
			return responseSuccess(userDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 修改登录密码
     * @return
     */
	@RequestMapping(value="/changePassword.do",method=RequestMethod.POST)
	@ResponseBody
	public String changePassword(HttpServletRequest request, int id, String newPassword) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			String loginIp=(String) request.getSession().getAttribute("loginIp");
			boolean result=userService.changePassword(adminId,loginIp,id, newPassword);
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
     * 修改支付密码
     * @return
     */
	@RequestMapping(value="/changePayPwd.do",method=RequestMethod.POST)
	@ResponseBody
	public String changePayPwd(HttpServletRequest request, int id, String newPayPwd) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			String loginIp=(String) request.getSession().getAttribute("loginIp");
			boolean result=userService.changePayPwd(adminId,loginIp,id, newPayPwd);
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
     * 换绑手机
     * @return
     */
	@RequestMapping(value="/rebindPhone.do",method=RequestMethod.POST)
	@ResponseBody
	public String rebindPhone(HttpServletRequest request, int id, String newPhone) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			String loginIp=(String) request.getSession().getAttribute("loginIp");
			UserDTO userDTO=userService.rebindPhone(request,adminId,loginIp, id, newPhone);
			return responseSuccess(userDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
