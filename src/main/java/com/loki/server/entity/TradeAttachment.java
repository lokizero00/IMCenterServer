package com.loki.server.entity;

import java.io.Serializable;

public class TradeAttachment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int tradeId;
	private String name;
	private boolean preview;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPreview() {
		return preview;
	}
	public void setPreview(boolean preview) {
		this.preview = preview;
	}
}
