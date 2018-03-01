package com.loki.server.entity;

import java.io.Serializable;

public class TradeReportAttachment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int tradeReportInformationId;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTradeReportInformationId() {
		return tradeReportInformationId;
	}
	public void setTradeReportInformationId(int tradeReportInformationId) {
		this.tradeReportInformationId = tradeReportInformationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
