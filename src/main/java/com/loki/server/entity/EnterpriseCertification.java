package com.loki.server.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class EnterpriseCertification implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int creatorId;
	private int updaterId;
	private int userId;
	private String position;
	private String enterpriseName;
	private String licensePic;
	private Timestamp verifyTime;
	private int adminVerifierId;
	private String status;
	private String refuseReason;
	private boolean deleted;
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

}
