package com.loki.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IntentionJournal implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String type;
	private int intentionId;
	private int userId;
	private String userName;
	private String innerBusiNo;
	private BigDecimal amount;
	private String state;
	private String checkState;
	private Timestamp opTime;
	private String thirdChannel;
	private String thirdTransNo;
	private Timestamp thirdReceiptTime;
	private BigDecimal thirdReceiptAmount;
	private int needThirdConfirm;
	private String memo;
	private String isReturn;
	private String outRequestNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIntentionId() {
		return intentionId;
	}
	public void setIntentionId(int intentionId) {
		this.intentionId = intentionId;
	}
	public String getInnerBusiNo() {
		return innerBusiNo;
	}
	public void setInnerBusiNo(String innerBusiNo) {
		this.innerBusiNo = innerBusiNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public Timestamp getOpTime() {
		return opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}
	public String getThirdChannel() {
		return thirdChannel;
	}
	public void setThirdChannel(String thirdChannel) {
		this.thirdChannel = thirdChannel;
	}
	public String getThirdTransNo() {
		return thirdTransNo;
	}
	public void setThirdTransNo(String thirdTransNo) {
		this.thirdTransNo = thirdTransNo;
	}
	public Timestamp getThirdReceiptTime() {
		return thirdReceiptTime;
	}
	public void setThirdReceiptTime(Timestamp thirdReceiptTime) {
		this.thirdReceiptTime = thirdReceiptTime;
	}
	public BigDecimal getThirdReceiptAmount() {
		return thirdReceiptAmount;
	}
	public void setThirdReceiptAmount(BigDecimal thirdReceiptAmount) {
		this.thirdReceiptAmount = thirdReceiptAmount;
	}
	public int getNeedThirdConfirm() {
		return needThirdConfirm;
	}
	public void setNeedThirdConfirm(int needThirdConfirm) {
		this.needThirdConfirm = needThirdConfirm;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	public String getOutRequestNo() {
		return outRequestNo;
	}
	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
