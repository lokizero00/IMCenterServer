package com.loki.server.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class EnterpriseCertificationDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int userId;
	private String position;
	private String enterpriseName;
	private String licensePic;
	private Timestamp verifyTime;
	private int adminVerifierId;
	private String status;
	private String refuseReason;
	private boolean deleted;
	
	private String userNickName;
	private String adminVerifierName;
	private String statusName;
	private String licensePicUrl;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getLicensePic() {
		return licensePic;
	}
	public void setLicensePic(String licensePic) {
		this.licensePic = licensePic;
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
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
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
	public String getLicensePicUrl() {
		return licensePicUrl;
	}
	public void setLicensePicUrl(String licensePicUrl) {
		this.licensePicUrl = licensePicUrl;
	}
}
