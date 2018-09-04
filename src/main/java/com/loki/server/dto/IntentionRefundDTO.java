package com.loki.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IntentionRefundDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int intentionId;
	private int userId;
	private Timestamp requestTime;
	private BigDecimal amount;
	private int refundItem;
	private Timestamp finishTime;
	private int journalId;
	private int state;
	private String outRequestNo;
	private int refundType;
	private int refundChannel;
	private String userName;
	private String phone;
	private String refundAccount;
	private int adminPayerId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIntentionId() {
		return intentionId;
	}
	public void setIntentionId(int intentionId) {
		this.intentionId = intentionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public int getRefundItem() {
		return refundItem;
	}
	public void setRefundItem(int refundItem) {
		this.refundItem = refundItem;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}
	public int getJournalId() {
		return journalId;
	}
	public void setJournalId(int journalId) {
		this.journalId = journalId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getOutRequestNo() {
		return outRequestNo;
	}
	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}
	public int getRefundType() {
		return refundType;
	}
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}
	public int getRefundChannel() {
		return refundChannel;
	}
	public void setRefundChannel(int refundChannel) {
		this.refundChannel = refundChannel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRefundAccount() {
		return refundAccount;
	}
	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}
	public int getAdminPayerId() {
		return adminPayerId;
	}
	public void setAdminPayerId(int adminPayerId) {
		this.adminPayerId = adminPayerId;
	}
	
	

}
