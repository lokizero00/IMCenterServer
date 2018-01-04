package com.loki.server.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.loki.server.service.UploadService;
import com.loki.server.utils.PropertyUtil;
import com.loki.server.utils.ServiceException;
import com.loki.server.utils.ServiceExceptionEnums;

@Service
@Transactional
public class UploadServiceImpl implements UploadService {

	@Override
	public boolean uploadFile(HttpServletRequest request,MultipartFile file) throws ServiceException,IOException{
		if(!file.isEmpty()) {
			try {
				String savePath = request.getSession().getServletContext().getRealPath("/")+PropertyUtil.getInstance().getPropertyValue("Common", "fileUploadPath");
				String filePath=savePath+file.getOriginalFilename();
				file.transferTo(new File(filePath));
				return true;
			}catch(IOException ioe) {
				ioe.printStackTrace();
				throw ioe;
			}
		}else {
			throw new ServiceException(ServiceExceptionEnums.FILE_NOT_FOUND);
		}
	}

}
