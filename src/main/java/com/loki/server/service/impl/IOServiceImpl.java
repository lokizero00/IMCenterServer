package com.loki.server.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.loki.server.dao.SettingDao;
import com.loki.server.entity.UploadFile;
import com.loki.server.service.IOService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.FileUtil;
import com.loki.server.utils.PropertyUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;

@Service
@Transactional
public class IOServiceImpl extends BaseService implements IOService {
	@Override
	public String uploadImage(HttpServletRequest request, MultipartFile file) throws ServiceException, IOException {
		if (!file.isEmpty()) {
			try {
				String originalName = file.getOriginalFilename();
				String prefix = originalName.substring(originalName.lastIndexOf(".") + 1);
				String fileName = CommonUtil.getInstance().getUUID() + "." + prefix;
				String savePath = request.getSession().getServletContext().getRealPath("/")
						+ PropertyUtil.getInstance().getPropertyValue("common", "imageUploadPath");
				String filePath = savePath + fileName;
				file.transferTo(new File(filePath));
				return fileName;
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw ioe;
			}
		} else {
			throw new ServiceException(ResultCodeEnums.FILE_NOT_FOUND);
		}
	}

	@Override
	public byte[] getImageFileByte(HttpServletRequest request, String imageName) throws IOException {
		String filePath = request.getSession().getServletContext().getRealPath("/")
				+ PropertyUtil.getInstance().getPropertyValue("common", "imageUploadPath");
		String fileUrl = filePath + imageName;
		byte[] fileBytes = FileUtil.toByteArray(fileUrl);
		return fileBytes;
	}

	@Override
	public UploadFile uploadImage(HttpServletRequest request) throws IOException {
		// 转换为文件类型的request
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获取相应file对象
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		Iterator<String> fileIterator = multipartRequest.getFileNames();
		String savePath = request.getSession().getServletContext().getRealPath("/")
				+ PropertyUtil.getInstance().getPropertyValue("common", "imageUploadPath");
		
		String requestPath =getImageRequestPath(request);
		
		while (fileIterator.hasNext()) {
			String fileKey = fileIterator.next();
			// 获取相应文件
			MultipartFile multipartFile = fileMap.get(fileKey);
			if (multipartFile.getSize() != 0L) {
				validateImage(multipartFile);
				// 调用saveImage方法保存
				UploadFile file = saveImage(multipartFile, savePath, requestPath);
				return file;
			}
		}
		return null;
	}

	private UploadFile saveImage(MultipartFile image, String savePath, String requestPath) throws IOException {
		String contentType = image.getContentType();
		String type = contentType.substring(contentType.indexOf("/") + 1);
		String fileName = CommonUtil.getInstance().getUUID() + "." + type;

		// 封装了一个简单的file对象，添加了几个属性
		UploadFile file = new UploadFile(savePath, fileName, requestPath);
		file.setContentType(contentType);

		// 通过org.apache.commons.io.FileUtils的writeByteArrayToFile对图片进行保存
		FileUtils.writeByteArrayToFile(file.getFile(), image.getBytes());

		return file;
	}

	private void validateImage(MultipartFile image) {
	}
}
