package com.loki.server.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WxPayProperties {
	
	private static WxPayProperties instance;
    
    public WxPayProperties() {}
    
    public static WxPayProperties getInstance() {
    		if(instance==null) {
    			instance=new WxPayProperties();
    		}
    		return instance;
    }

    /**
     * 设置微信公众号的appid
     */
    private String appId="wx6fa2b67649955009";

    /**
     * 微信支付商户号
     */
    private String mchId="1507414721";

    /**
     * 微信支付商户密钥
     */
    private String mchKey="3CHzwBegqpl07kzAvjT0s3KS3QPKNJ1V";

    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    private String subMchId;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath="/home/cert/wxpay/apiclient_cert.p12";
    
    private String appSecret="1880b57f05c8b604e63c70510f9e2d63";

    public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/**
     * 微信支付异步回掉地址，通知url必须为直接可访问的url，不能携带参数。
     */
    private String notifyUrl="https://www.bestimade.com/payNotify/unified/wxpay";

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getNotifyUrl() {
        return this.notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
