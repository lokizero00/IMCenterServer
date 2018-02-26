package com.loki.server.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

import com.loki.server.entity.UploadFile;
import com.loki.server.service.IOService;
import com.loki.server.utils.ServiceException;

@Controller
@RequestMapping("/s")
public class IOController extends BaseController{
	private static final Logger logger = Logger.getLogger(IOController.class);
	@Autowired
	public IOService ioService;
	
	@RequestMapping(value="/io/uploadImage",method=RequestMethod.POST)
    public void uploadImage(HttpServletRequest request,HttpServletResponse response,PrintWriter out) {
        logger.debug("获取上传文件...");
        try {
            UploadFile uploadFiles = ioService.uploadImage(request);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("pragma", "no-cache");
            response.setHeader("cache-control", "no-cache");
            out.write(responseSuccess(uploadFiles));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getMessage(), e);
            out.write(responseFail("文件上传失败"));
        }
    }
	
	//图片上传
	@RequestMapping(value="/api/io/uploadImageMobile",method=RequestMethod.POST)
	public String imageUploadMobile(HttpServletRequest request,ModelMap mm) {
		MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
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
	@RequestMapping(value = "/io/getImage", method = RequestMethod.GET)
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
