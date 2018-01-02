package com.loki.server.utils;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
	
	public static String getFileUploadPath() {
		return ResourceBundle.getBundle("common").getString("fileUploadPath");
	}
}
