package com.loki.server.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TradeLog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int tradeId;
	private String content;
	private Timestamp createTime;
	private String logRole;
	private int logOperatorId;
	private String logState;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getLogRole() {
		return logRole;
	}
	public void setLogRole(String logRole) {
		this.logRole = logRole;
	}
	public int getLogOperatorId() {
		return logOperatorId;
	}
	public void setLogOperatorId(int logOperatorId) {
		this.logOperatorId = logOperatorId;
	}
	public String getLogState() {
		return logState;
	}
	public void setLogState(String logState) {
		this.logState = logState;
	}
}
