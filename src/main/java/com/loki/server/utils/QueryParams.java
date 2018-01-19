package com.loki.server.utils;

import java.io.Serializable;
import java.sql.Timestamp;

public class QueryParams implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Timestamp createTimeStart;
	private Timestamp createTimeEnd;
	private Timestamp updateTimeStart;
	private Timestamp updateTimeEnd;
	
	private int creatorId;
	private int updaterId;
	
	private int adminCreatorId;
	private int adminUpdaterId;
	
	private String title;
	private String type;
	private int relationId;
	
	private int userId;
	private boolean isRead;
	public Timestamp getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(Timestamp createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public Timestamp getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Timestamp createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Timestamp getUpdateTimeStart() {
		return updateTimeStart;
	}
	public void setUpdateTimeStart(Timestamp updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}
	public Timestamp getUpdateTimeEnd() {
		return updateTimeEnd;
	}
	public void setUpdateTimeEnd(Timestamp updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
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
	public int getAdminCreatorId() {
		return adminCreatorId;
	}
	public void setAdminCreatorId(int adminCreatorId) {
		this.adminCreatorId = adminCreatorId;
	}
	public int getAdminUpdaterId() {
		return adminUpdaterId;
	}
	public void setAdminUpdaterId(int adminUpdaterId) {
		this.adminUpdaterId = adminUpdaterId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRelationId() {
		return relationId;
	}
	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
}
