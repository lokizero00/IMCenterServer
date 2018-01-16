package com.loki.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class TradeDocking implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int creatorId;
	private int updaterId;
	private int userId;
	private int tradeId;
	private int identityId;
	private int enterpriseId;
	private BigDecimal offer;
	private BigDecimal intention;
	private String message;
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
	public int getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(int updaterId) {
		this.updaterId = updaterId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}
	public int getIdentityId() {
		return identityId;
	}
	public void setIdentityId(int identityId) {
		this.identityId = identityId;
	}
	public int getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public BigDecimal getOffer() {
		return offer;
	}
	public void setOffer(BigDecimal offer) {
		this.offer = offer;
	}
	public BigDecimal getIntention() {
		return intention;
	}
	public void setIntention(BigDecimal intention) {
		this.intention = intention;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
