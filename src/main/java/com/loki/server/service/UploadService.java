package com.loki.server.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.loki.server.utils.ServiceException;

public interface UploadService {
	boolean uploadFile(HttpServletRequest request,MultipartFile file) throws ServiceException,IOException;
}
