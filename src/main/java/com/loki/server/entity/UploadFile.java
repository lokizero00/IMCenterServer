package com.loki.server.entity;

import java.io.File;

public class UploadFile {
	private String saveDirectory;// 文件保存路径
	private String fileName;
	private String contentType;
	private String requestFileUrl;// 请求文件的url

	public UploadFile(String saveDirectory, String filesystemName, String requestFilePath) {
		this.saveDirectory = saveDirectory;
		this.fileName = filesystemName;
		this.requestFileUrl = requestFilePath + "?name=" + getFileName();
	}

	public String getFileName() {
		return fileName;
	}

	public String getSaveDirectory() {
		return saveDirectory;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setSaveDirectory(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		if (getSaveDirectory() == null || getFileName() == null) {
			return null;
		} else {
			return new File(getSaveDirectory() + "/" + getFileName());
		}
	}

	public String getRequestFileUrl() {
		return requestFileUrl;
	}

	public void setRequestFileUrl(String requestFileUrl) {
		this.requestFileUrl = requestFileUrl;
	}
}
