package com.loki.server.utils;

import java.util.ResourceBundle;

public class PropertyUtil {
	private static PropertyUtil instance;
	
	public static PropertyUtil getInstance() {
		if(null == instance) {
			instance = new PropertyUtil();
		}
		return instance;
	}
	
	public String getPropertyValue(String fileName,String key) {
		return ResourceBundle.getBundle(fileName).getString(key);
	}
}
