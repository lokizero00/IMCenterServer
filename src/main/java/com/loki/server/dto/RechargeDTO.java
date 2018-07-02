package com.loki.server.dto;

import java.io.Serializable;

public class RechargeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	//支付方式 1、支付宝 2、微信支付 3、余额支付
    private Integer payType;
    //微信支付对象
    private WxRechargeDTO wxRechargeDTO;
    //支付宝支付对象
    private AliRechargeDTO aliRechargeDTO;
    //余额支付对象
    private BalancePayDTO balancePayDTO;
    //余额支付返回状态true 成功   false 失败
    private boolean falg;
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public WxRechargeDTO getWxRechargeDTO() {
		return wxRechargeDTO;
	}
	public void setWxRechargeDTO(WxRechargeDTO wxRechargeDTO) {
		this.wxRechargeDTO = wxRechargeDTO;
	}
	public AliRechargeDTO getAliRechargeDTO() {
		return aliRechargeDTO;
	}
	public void setAliRechargeDTO(AliRechargeDTO aliRechargeDTO) {
		this.aliRechargeDTO = aliRechargeDTO;
	}
	public BalancePayDTO getBalancePayDTO() {
		return balancePayDTO;
	}
	public void setBalancePayDTO(BalancePayDTO balancePayDTO) {
		this.balancePayDTO = balancePayDTO;
	}
	public boolean isFalg() {
		return falg;
	}
	public void setFalg(boolean falg) {
		this.falg = falg;
	}
}
