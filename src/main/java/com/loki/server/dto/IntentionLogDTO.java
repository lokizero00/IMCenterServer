package com.loki.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IntentionLogDTO implements Serializable{
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
	
	private String logOperatorName;
	private String logRoleName;
	private String relationTypeName;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getIntentionId() {
		return intentionId;
	}
	public void setIntentionId(int intentionId) {
		this.intentionId = intentionId;
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
	public String getLogOperatorName() {
		return logOperatorName;
	}
	public void setLogOperatorName(String logOperatorName) {
		this.logOperatorName = logOperatorName;
	}
	public String getLogRoleName() {
		return logRoleName;
	}
	public void setLogRoleName(String logRoleName) {
		this.logRoleName = logRoleName;
	}
	public String getRelationTypeName() {
		return relationTypeName;
	}
	public void setRelationTypeName(String relationTypeName) {
		this.relationTypeName = relationTypeName;
	}
}
