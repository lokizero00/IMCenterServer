package com.loki.server.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IntentionLog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private BigDecimal availableAmount;
	private BigDecimal freezeAmount;
	private String description;
	private int userId;
	private int intentionId;
	private int adminId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}
	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}
	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getIntentionId() {
		return intentionId;
	}
	public void setIntentionId(int intentionId) {
		this.intentionId = intentionId;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
}
