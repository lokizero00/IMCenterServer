package com.loki.server.model;

import java.io.Serializable;

public class TradePaycode implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int paycodeId;
	private int paycodeName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPaycodeId() {
		return paycodeId;
	}
	public void setPaycodeId(int paycodeId) {
		this.paycodeId = paycodeId;
	}
	public int getPaycodeName() {
		return paycodeName;
	}
	public void setPaycodeName(int paycodeName) {
		this.paycodeName = paycodeName;
	}
}
