package com.loki.server.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/s/session")
public class SessionController extends BaseController{
	/**
	 * 获取Session中的对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getObject.do")
	@ResponseBody
	public String getObject(HttpSession httpSession,String sessionKey) {
		try {
			Object obj=httpSession.getAttribute(sessionKey);
			return responseSuccess(obj);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
	 * 获取Session中的对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getArray.do")
	@ResponseBody
	public String getArray(HttpSession httpSession,String sessionKey) {
		try {
			List<Object> list=(List) httpSession.getAttribute(sessionKey);
			return responseArraySuccess(list);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
