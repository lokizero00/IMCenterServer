package com.loki.server.utils;

public class ServiceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private ExceptionEnums exceptionEnums;
	
	public ServiceException(ExceptionEnums exceptionEnums){  
        this.exceptionEnums = exceptionEnums;  
	}  
	public ExceptionEnums getExceptionEnums(){  
	    return exceptionEnums;  
	} 
	
	public String getMessage() {
		return exceptionEnums.getMessage();
	}
}
