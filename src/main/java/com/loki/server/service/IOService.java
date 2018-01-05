package com.loki.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.loki.server.utils.ServiceException;

public interface IOService {
	String uploadImage(HttpServletRequest request,MultipartFile file) throws ServiceException,IOException;
	byte[] getImageFileByte(HttpServletRequest request,String imageName) throws IOException;
}
