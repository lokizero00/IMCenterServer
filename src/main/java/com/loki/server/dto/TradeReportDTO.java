package com.loki.server.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TradeReportDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int creatorId;
	private int tradeId;
	private int informerId;
	private int delinquentId;
	private String status;
	private Timestamp verifyTime;
	private int adminVerifierId;
	private boolean tradeOwner;

	private String informerName;
	private String delinquentName;
	private String statusName;
	private String adminVerifierName;

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

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public int getInformerId() {
		return informerId;
	}

	public void setInformerId(int informerId) {
		this.informerId = informerId;
	}

	public int getDelinquentId() {
		return delinquentId;
	}

	public void setDelinquentId(int delinquentId) {
		this.delinquentId = delinquentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Timestamp verifyTime) {
		this.verifyTime = verifyTime;
	}

	public int getAdminVerifierId() {
		return adminVerifierId;
	}

	public void setAdminVerifierId(int adminVerifierId) {
		this.adminVerifierId = adminVerifierId;
	}

	public String getInformerName() {
		return informerName;
	}

	public void setInformerName(String informerName) {
		this.informerName = informerName;
	}

	public String getDelinquentName() {
		return delinquentName;
	}

	public void setDelinquentName(String delinquentName) {
		this.delinquentName = delinquentName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAdminVerifierName() {
		return adminVerifierName;
	}

	public void setAdminVerifierName(String adminVerifierName) {
		this.adminVerifierName = adminVerifierName;
	}

	public boolean isTradeOwner() {
		return tradeOwner;
	}

	public void setTradeOwner(boolean tradeOwner) {
		this.tradeOwner = tradeOwner;
	}
}
