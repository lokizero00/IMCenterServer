package com.loki.server.utils;

import java.util.ResourceBundle;

public class CommonUtil {
	
	public static String getFileUploadPath() {
		return ResourceBundle.getBundle("common").getString("fileUploadPath");
	}
}
