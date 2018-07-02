package com.loki.server.dto;

import java.io.Serializable;

public class AliRechargeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	//支付宝支付信息
    private String orderInfo;
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
}
