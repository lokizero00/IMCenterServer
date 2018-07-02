package com.loki.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class IntentionRechargeAli implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private int journalId;
	private int userId;
	private String rtvCode;
	private String rtvMsg;
	private String subCode;
	private String subMsg;
	private String sign;
	private String tradeNo;
	private String outTradeNo;
	private String buyerLogonId;
	private BigDecimal totalAmount;
	private BigDecimal receiptAmount;
	private BigDecimal buyerPayAmount;
	private BigDecimal pointAmount;
	private BigDecimal invoiceAmount;
	private Timestamp gmtPayment;
	private BigDecimal cardBalance;
	private String storeName;
	private String buyerUserId;
	private String discountGoodsDetail;
	private String fundBillList;
	private String voucherDetailList;
	private Timestamp createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getJournalId() {
		return journalId;
	}
	public void setJournalId(int journalId) {
		this.journalId = journalId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRtvCode() {
		return rtvCode;
	}
	public void setRtvCode(String rtvCode) {
		this.rtvCode = rtvCode;
	}
	public String getRtvMsg() {
		return rtvMsg;
	}
	public void setRtvMsg(String rtvMsg) {
		this.rtvMsg = rtvMsg;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubMsg() {
		return subMsg;
	}
	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getBuyerLogonId() {
		return buyerLogonId;
	}
	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}
	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	public BigDecimal getBuyerPayAmount() {
		return buyerPayAmount;
	}
	public void setBuyerPayAmount(BigDecimal buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}
	public BigDecimal getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public Timestamp getGmtPayment() {
		return gmtPayment;
	}
	public void setGmtPayment(Timestamp gmtPayment) {
		this.gmtPayment = gmtPayment;
	}
	public BigDecimal getCardBalance() {
		return cardBalance;
	}
	public void setCardBalance(BigDecimal cardBalance) {
		this.cardBalance = cardBalance;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getBuyerUserId() {
		return buyerUserId;
	}
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	public String getDiscountGoodsDetail() {
		return discountGoodsDetail;
	}
	public void setDiscountGoodsDetail(String discountGoodsDetail) {
		this.discountGoodsDetail = discountGoodsDetail;
	}
	public String getFundBillList() {
		return fundBillList;
	}
	public void setFundBillList(String fundBillList) {
		this.fundBillList = fundBillList;
	}
	public String getVoucherDetailList() {
		return voucherDetailList;
	}
	public void setVoucherDetailList(String voucherDetailList) {
		this.voucherDetailList = voucherDetailList;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
