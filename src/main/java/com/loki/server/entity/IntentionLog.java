package com.loki.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IntentionLog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private BigDecimal availableAmount;
	private BigDecimal changeAmount;
	private String content;
	private String relationType;
	private int relationId;
	private int intentionId;
	private String logRole;
	private int logOperatorId;
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
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
	public int getIntentionId() {
		return intentionId;
	}
	public void setIntentionId(int intentionId) {
		this.intentionId = intentionId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public int getRelationId() {
		return relationId;
	}
	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}
}
