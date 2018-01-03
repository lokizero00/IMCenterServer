package com.loki.server.model;

import java.io.Serializable;

public class TradeReportAttachment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int tradeReportInformationId;
	private String url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
