package com.loki.server.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @Author: lokizero00
 * @Description:
 * @Date: Created in 16:11 2018/8/26 
 * @Modified by:
 */
@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "xml")
// 控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
        "mch_appid",
        "mchid",
        "nonce_str",
        "sign",
        "partner_trade_no",
        "openid",
        "check_name",
        "re_user_name",
        "amount",
        "desc",
        "spbill_create_ip",
})
@Data
public class Transfer implements Serializable{

    public String getMch_appid() {
		return mch_appid;
	}

	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPartner_trade_no() {
		return partner_trade_no;
	}

	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCheck_name() {
		return check_name;
	}

	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}

	public String getRe_user_name() {
		return re_user_name;
	}

	public void setRe_user_name(String re_user_name) {
		this.re_user_name = re_user_name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	private static final long serialVersionUID = 1L;

    /** 商户账号appid*/
    public String mch_appid;

    /** 微信支付商户号*/
    public String mchid;

    /** 随机串*/
    public String nonce_str;

    /** 签名*/
    public String sign;

    /** 商户订单号*/
    public String partner_trade_no;

    /** 用户id*/
    public String openid;

    /** 是否校验用户姓名 NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名*/
    public String check_name="NO_CHECK";

    private String re_user_name;

    /** 金额 单位：分*/
    public int amount;

    /** 企业付款描述信息*/
    public String desc="互联制造意向金提现";

    /** ip地址*/
    public String spbill_create_ip;

}
