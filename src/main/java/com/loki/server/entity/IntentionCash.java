package com.loki.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IntentionCash implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int creatorId;
	private int userId;
	private String sn;
	private int intentionId;
	private BigDecimal amount;
	private String payCode;
	private String payName;
	private String status;
	private int adminUpdaterId;
	private Timestamp payTime;
	private int adminPayerId;
	private String refuseReason;
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
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public int getIntentionId() {
		return intentionId;
	}
	public void setIntentionId(int intentionId) {
		this.intentionId = intentionId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAdminUpdaterId() {
		return adminUpdaterId;
	}
	public void setAdminUpdaterId(int adminUpdaterId) {
		this.adminUpdaterId = adminUpdaterId;
	}
	public Timestamp getPayTime() {
		return payTime;
	}
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}
	public int getAdminPayerId() {
		return adminPayerId;
	}
	public void setAdminPayerId(int adminPayerId) {
		this.adminPayerId = adminPayerId;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
}
