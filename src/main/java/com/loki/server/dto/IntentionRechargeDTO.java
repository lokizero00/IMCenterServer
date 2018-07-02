package com.loki.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class IntentionRechargeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private Integer payType;
	private BigDecimal rechargeAmount;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
}
