package com.loki.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.loki.server.service.UploadService;
import com.loki.server.utils.ServiceException;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	public UploadService service;
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject upload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
		System.out.println("获取到的 parameter id=:"+multipartHttpServletRequest.getParameter("id"));
		JSONObject object = new JSONObject();
		try {
			service.uploadFile(request,multipartHttpServletRequest.getFile("file"));
			object.put("status", "ok");
			object.put("msg", "上传成功");
		}catch(IOException ioe) {
			object.put("status", "fail");
			object.put("error", ioe.getMessage());
		}catch(ServiceException se) {
			object.put("status", "fail");
			object.put("error", se.getMessage());
		}
		
		return object;
	}
}
