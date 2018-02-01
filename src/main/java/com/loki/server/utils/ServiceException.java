package com.loki.server.utils;

public class ServiceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private ResultCodeEnums exceptionEnums;
	
	public ServiceException(ResultCodeEnums exceptionEnums){  
        this.exceptionEnums = exceptionEnums;  
	}  
	public ResultCodeEnums getExceptionEnums(){  
	    return exceptionEnums;  
	} 
	
	public String getMessage() {
		return exceptionEnums.getMessage();
	}
}
