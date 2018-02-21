package com.loki.server.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class IdentityCertificationDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int userId;
	private String trueName;
	private String identityNumber;
	private String identityFront;
	private String identityBack;
	private Timestamp verifyTime;
	private int adminVerifierId;
	private String status;
	private String refuseReason;
	
	private String userNickName;
	private String adminVerifierName;
	private String statusName;
	private String identityFrontUrl;
	private String identityBackUrl;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	public String getIdentityFront() {
		return identityFront;
	}
	public void setIdentityFront(String identityFront) {
		this.identityFront = identityFront;
	}
	public String getIdentityBack() {
		return identityBack;
	}
	public void setIdentityBack(String identityBack) {
		this.identityBack = identityBack;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public String getAdminVerifierName() {
		return adminVerifierName;
	}
	public void setAdminVerifierName(String adminVerifierName) {
		this.adminVerifierName = adminVerifierName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getIdentityFrontUrl() {
		return identityFrontUrl;
	}
	public void setIdentityFrontUrl(String identityFrontUrl) {
		this.identityFrontUrl = identityFrontUrl;
	}
	public String getIdentityBackUrl() {
		return identityBackUrl;
	}
	public void setIdentityBackUrl(String identityBackUrl) {
		this.identityBackUrl = identityBackUrl;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
}
