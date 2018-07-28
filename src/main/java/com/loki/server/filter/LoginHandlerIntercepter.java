package com.loki.server.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.loki.server.dao.UserTokenDao;

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
		if (requestURI.indexOf("api") > 0) {
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
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("userName");
			if (userName != null) {
				boolean isSuperAdmin = (boolean) session.getAttribute("superAdmin");
				if (!isSuperAdmin) {
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
				request.getRequestDispatcher("/error/redirectLogin.jsp").forward(request, response);
				return false;
			}

		}
	}

}
