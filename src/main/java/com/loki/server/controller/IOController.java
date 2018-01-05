package com.loki.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.loki.server.service.IOService;
import com.loki.server.utils.ServiceException;

@Controller
@RequestMapping("/api/io")
public class IOController {
	
	@Autowired
	public IOService ioService;
	
	//图片上传
	@RequestMapping(value="/imageUpload",method=RequestMethod.POST)
	public String imageUpload(HttpServletRequest request,ModelMap mm) {
		MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
//		System.out.println("获取到的 parameter id=:"+multipartHttpServletRequest.getParameter("id"));
		try {
			String fileName=ioService.uploadImage(request,multipartHttpServletRequest.getFile("file"));
			if(null!=fileName && ""!=fileName) {
				mm.addAttribute("resultCode", "1");
				mm.addAttribute("msg", "上传成功");
				mm.addAttribute("resultObj", fileName);
			}else {
				mm.addAttribute("resultCode", "14");
				mm.addAttribute("msg", "上传失败");
			}
		}catch(IOException ioe) {
			mm.addAttribute("resultCode", "14");
			mm.addAttribute("msg", "上传失败:"+ioe.getMessage());
		}catch(ServiceException se) {
			mm.addAttribute("resultCode", "14");
			mm.addAttribute("msg", "上传失败:"+se.getMessage());
		}
		
		return "mobileResultJson";
	}
	
	//获取图片
	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public ResponseEntity<byte[]> image(HttpServletRequest request,String name){
		byte[] zp=null;
		try {
			zp=ioService.getImageFileByte(request, name);
			if (null == zp) {
				return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<byte[]>(zp, headers, HttpStatus.OK);
		}catch(IOException ioe) {
			return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
		}
		
	}
}
