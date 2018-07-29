package com.loki.server.utils;

public enum ResultCodeEnums {
	SUCCESS(1,"成功"),
	UNKNOW_ERROR(-1,"未知错误"),
	USER_NOT_EXIST(-2,"用户名或密码错误"),
	PARAM_ERROR(-3,"参数错误"),
	TOKEN_EXPIRED(-4,"令牌已失效"),
	PHONE_EXISTS(-5,"手机号已存在"),
	SAVE_FAIL(-6,"保存失败"),
	UPDATE_FAIL(-7,"更新失败"),
	NOT_ALLOW_EDIT(-8,"禁止修改"),
	AUTH_CODE_SEND_FAIL(-9,"验证码发送失败"),
	AUTH_CODE_WRONG(-11,"验证码错误"),
	AUTH_CODE_TIME_OUT(-12,"验证码超时"),
	TOKEN_INVALID(-13,"令牌校验错误"),
	UPLOAD_FAIL(-14,"上传失败"),
	CERTIFICATION_NOT_EXIST(-15,"身份认证不存在(实名/企业)"),
	INTENTION_NOT_EXIST(-16,"意向金账户不存在"),
	FILE_NOT_FOUND(-17,"文件没有找到"),
	DELETE_FAIL(-18,"删除失败"),
	BANK_CODE_ERROR(-19,"银行代码错误"),
	NOTICE_NOT_EXIST(-20,"消息不存在"),
	RELATION_DATA_ERROR(-21,"关联数据错误"),
	DATA_QUERY_FAIL(-22,"数据查询失败"),
	INTENTION_AVAILABLE_NOT_ENOUGH(-23,"可用意向金不足"),
	DATA_INVALID(-24,"数据校验错误"),
	LOGIN_FAIL(-25,"登录失败"),
	LOGIN_DATA_INVALID(-26,"用户名或密码错误"),
	USER_OUT_OF_SERVICE(-27,"账户已停用"),
	DATA_CONVERT_FAIL(-28,"数据转换失败"),
	DUPLICATE_SUBMIT(-29,"重复提交"),
	TRANACTION_ERROR_ZERO_AMOUNT(-30,"交易失败，订单金额必须大于0元"),
	TRANACTION_NOTIFY_ERROR(-31,"支付回调接口异常"),
	TRANACTION_UNKNOW_ERROR(-32,"支付失败，未知错误"),
	TRANACTION_WEIXIN_RETURN_ERROR(-33,"微信返回参数错误"),
	TRANACTION_ALI_RETURN_ERROR(-34,"支付宝返回参数错误"),
	TRANACTION_WEIXIN_CLOSE_ERROR(-35,"微信订单关闭错误"),
	TRANACTION_ALI_CLOSE_ERROR(-35,"支付宝订单关闭错误"),
	RECHARGE_AMOUNT_ZERO(-36,"充值金额错误"),
	AUTH_RESOURCE_FAIL(-37,"资源授权失败"),
	ADMIN_NOT_EXIST(-38,"管理员不存在"),
	ADMIN_DELETE_ROLE_FAIL(-39,"解绑角色失败"),
	ROLE_NOT_EXIST(-40,"角色不可用")
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
