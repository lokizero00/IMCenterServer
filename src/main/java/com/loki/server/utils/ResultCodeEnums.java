package com.loki.server.utils;

public enum ResultCodeEnums {
	SUCCESS(1,"成功"),
	UNKNOW_ERROR(-1,"未知错误"),
	USER_NOT_EXIST(-2,"用户不存在"),
	PARAM_ERROR(-3,"参数错误"),
	TOKEN_EXPIRED(-4,"令牌已失效"),
	PHONE_EXISTS(-5,"手机号已存在"),
	SAVE_FAIL(-6,"保存失败"),
	UPDATE_FAIL(-7,"更新失败"),
	NOT_ALLOW_EDIT(-8,"禁止修改"),
	AUTH_CODE_SEND_FAIL(-9,"验证码发送失败"),
	AUTH_CODE_SAVE_FAIL(-10,"验证码保存失败"),
	AUTH_CODE_WRONG(-11,"验证码错误"),
	AUTH_CODE_TIME_OUT(-12,"验证码超时"),
	TOKEN_INVALID(-13,"令牌校验错误"),
	UPLOAD_FAIL(-14,"上传失败"),
	IDENTITY_CERTIFICATION_NOT_EXIST(-15,"实名认证不存在"),
	INTENTION_NOT_EXIST(-16,"意向金账户不存在"),
	FILE_NOT_FOUND(-17,"文件没有找到"),
	DELETE_FAIL(-18,"删除失败"),
	BANK_CODE_ERROR(-19,"银行代码错误"),
	NOTICE_NOT_EXIST(-20,"消息不存在"),
	RELATION_DATA_ERROR(-21,"关联数据错误"),
	DATA_QUERY_FAIL(-22,"数据查询失败")
	;

	public int code;  
    public String message;  
      
    private ResultCodeEnums(int code, String message){  
        this.code = code;  
        this.message = message;  
    }
     
    public int getCode() {  
        return code;  
    }  
   
    public String getMessage() {  
        return message;  
    }  
}
