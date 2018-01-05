package com.loki.server.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.loki.server.service.IOService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.FileUtil;
import com.loki.server.utils.PropertyUtil;
import com.loki.server.utils.ServiceException;
import com.loki.server.utils.ServiceExceptionEnums;

@Service
@Transactional
public class IOServiceImpl implements IOService {

	@Override
	public String uploadImage(HttpServletRequest request,MultipartFile file) throws ServiceException,IOException{
		if(!file.isEmpty()) {
			try {
				String originalName=file.getOriginalFilename();
				String prefix=originalName.substring(originalName.lastIndexOf(".")+1);
				String fileName=CommonUtil.getInstance().getUUID()+"."+prefix;
				String savePath = request.getSession().getServletContext().getRealPath("/")+PropertyUtil.getInstance().getPropertyValue("common", "imageUploadPath");
				String filePath=savePath+fileName;
				file.transferTo(new File(filePath));
				return fileName;
			}catch(IOException ioe) {
				ioe.printStackTrace();
				throw ioe;
			}
		}else {
			throw new ServiceException(ServiceExceptionEnums.FILE_NOT_FOUND);
		}
	}

	@Override
	public byte[] getImageFileByte(HttpServletRequest request,String imageName) throws IOException {
		String filePath = request.getSession().getServletContext().getRealPath("/")+PropertyUtil.getInstance().getPropertyValue("common", "imageUploadPath");
		String fileUrl=filePath+imageName;
		byte[] fileBytes=FileUtil.toByteArray(fileUrl);
		return fileBytes;
	}

}
