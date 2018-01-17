package com.loki.server.entity;

import java.io.Serializable;

public class TradePaycode implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int tradeId;
	private int code;
	private int value;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
