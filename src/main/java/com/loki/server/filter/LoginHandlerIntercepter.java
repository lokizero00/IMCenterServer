package com.loki.server.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.loki.server.dao.UserTokenDao;

/**
 * 登陆拦截器 场景：用户点击查看的时候，我们进行登陆拦截器操作，判断用户是否登陆？ 登陆，则不拦截，没登陆，则转到登陆界面； TODO 作者：loki
 * 时间：2017年12月8日 下午13:21:33 工程：IMCenterServer
 */
public class LoginHandlerIntercepter implements HandlerInterceptor {
	@Autowired
	UserTokenDao userTokenDao;

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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String requestURI = request.getRequestURI();
		// 手机端
		if (requestURI.indexOf("api") > 0) {
			// 客户端登录验证，使用token令牌
			String token = request.getParameter("token");
			if (null != token && "" != token) {
				int tokenCount = userTokenDao.tokenCheck(token);
				if (tokenCount > 0) {
					return true;
				}
			}
			request.getRequestDispatcher("/api/login/tokenInvalid").forward(request, response);
			return false;
		} else {
			// web端
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("userName");
			if (userName != null) {
				boolean isSuperAdmin = (boolean) session.getAttribute("superAdmin");
				if (!isSuperAdmin) {
					//非超级管理员，需要对action进行url校验
					@SuppressWarnings("unchecked")
					List<String> permissionList=(List<String>) session.getAttribute("permissionList");
					if(permissionList.contains(requestURI)) {
						return true;
					}else {
						request.getRequestDispatcher("/error/no_permission.jsp").forward(request, response);
						return false;
					}
				} else {
					return true;
				}
			} else {
				// 没有登录，跳转到登录页
				request.getRequestDispatcher("/error/redirectLogin.jsp").forward(request, response);
				return false;
			}

		}
	}

}
