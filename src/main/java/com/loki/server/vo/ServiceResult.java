package com.loki.server.vo;

import java.io.Serializable;

import com.loki.server.utils.ResultCodeEnums;

public class ServiceResult <T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ResultCodeEnums resultCode;
	private T resultObj;
	public ResultCodeEnums getResultCode() {
		return resultCode;
	}
	public void setResultCode(ResultCodeEnums resultCode) {
		this.resultCode = resultCode;
	}
	public T getResultObj() {
		return resultObj;
	}
	public void setResultObj(T resultObj) {
		this.resultObj = resultObj;
	}
}
