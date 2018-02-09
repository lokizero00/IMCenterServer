package com.loki.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.loki.server.entity.UploadFile;
import com.loki.server.utils.ServiceException;

public interface IOService {
	/**
	 * mobile端上传图片
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	String uploadImage(HttpServletRequest request, MultipartFile file) throws ServiceException, IOException;

	/**
	 * 通过url获取图片
	 * 
	 * @param request
	 * @param imageName
	 * @return
	 * @throws IOException
	 */
	byte[] getImageFileByte(HttpServletRequest request, String imageName) throws IOException;

	/**
	 * web端上传图片
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	UploadFile uploadImage(HttpServletRequest request) throws IOException;
}
