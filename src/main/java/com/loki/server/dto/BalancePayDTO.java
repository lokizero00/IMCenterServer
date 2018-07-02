package com.loki.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalancePayDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	//账户余额
    private String amount;
    //内部业务编号
    private String innerBusiNo;
    //需要支付的费用
    private BigDecimal fee;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getInnerBusiNo() {
		return innerBusiNo;
	}
	public void setInnerBusiNo(String innerBusiNo) {
		this.innerBusiNo = innerBusiNo;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
