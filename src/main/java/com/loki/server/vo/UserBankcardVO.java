package com.loki.server.vo;

import java.io.Serializable;

public class UserBankcardVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private String bankCode;
	private String bankName;
	private String cardTypeCode;
	private String cardTypeName;
	private String cardNumber;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardTypeCode() {
		return cardTypeCode;
	}
	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	public String getCardTypeName() {
		return cardTypeName;
	}
	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
