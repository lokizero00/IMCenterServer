package com.loki.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class IntentionRefundRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private BigDecimal amount;
	private int refundItem;
	private int refundType;
	private int refundChannel;
	public int getRefundChannel() {
		return refundChannel;
	}
	public void setRefundChannel(int refundChannel) {
		this.refundChannel = refundChannel;
	}
	private String refundAccount;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getRefundItem() {
		return refundItem;
	}
	public void setRefundItem(int refundItem) {
		this.refundItem = refundItem;
	}
	public int getRefundType() {
		return refundType;
	}
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}
	public String getRefundAccount() {
		return refundAccount;
	}
	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}
}
