package com.loki.server.utils;

public enum ServiceExceptionEnums implements ExceptionEnums{
	FILE_NOT_FOUND(10001,"文件不存在");

	public int code;  
    public String message;  
      
    private ServiceExceptionEnums(int code, String message){  
        this.code = code;  
        this.message = message;  
    }
    
    @Override  
    public int getCode() {  
        return code;  
    }  
  
    @Override  
    public String getMessage() {  
        return message;  
    }  
	
}
