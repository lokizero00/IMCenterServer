package com.loki.server.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.loki.server.dao.UserTokenDao;

/** 
 * 登陆拦截器 
 * 场景：用户点击查看的时候，我们进行登陆拦截器操作，判断用户是否登陆？ 
 * 登陆，则不拦截，没登陆，则转到登陆界面； 
 * TODO 
 * 作者：loki 
 * 时间：2017年12月8日 下午13:21:33 
 * 工程：IMCenterServer 
 */
public class LoginHandlerIntercepter implements HandlerInterceptor{
	@Autowired UserTokenDao userTokenDao;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
		String requestURI=request.getRequestURI();
		
		//TODO 编写拦截器
		//手机端
		if(requestURI.indexOf("Mobile/")>0) {
			if(requestURI.indexOf("/userRegist")>0 || requestURI.indexOf("/userLogin")>0) {
				return true;
			}else {
				//客户端登录验证，使用token令牌
				String token = request.getParameter("token");
				int tokenCount=userTokenDao.tokenCheck(token);
				if (tokenCount>0) {
					return true;
				}else {
					return false;
				}
			}
		}else {
			if(requestURI.indexOf("login/adminLogin")>0) {
				return true;
			}else {
				//编辑页面下
//				if(requestURI.indexOf("editClientIfo")>0) {
					HttpSession session=request.getSession();
					String userName=(String) session.getAttribute("userName");
					if(userName != null) {
						//成功登陆的用户
						
						return true;
					}else{
						//没有登录，跳转到登录页
						request.getRequestDispatcher("/login.jsp").forward(request, arg1);
						return false;
					}
//				}else {
//					return true;
//				}
			}
		}
	}
	
}
