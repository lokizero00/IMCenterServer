package com.loki.server.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class NoticeVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int adminCreatorId;
	private int adminUpdaterId;
	private String title;
	private String content;
	private String relationType;
	private int relationId;
	private boolean send;
	private String relationTypeName;
	private String adminCreatorName;
	private String adminUpdaterName;
	
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
	public boolean isSend() {
		return send;
	}
	public void setSend(boolean send) {
		this.send = send;
	}
	public String getRelationTypeName() {
		return relationTypeName;
	}
	public void setRelationTypeName(String relationTypeName) {
		this.relationTypeName = relationTypeName;
	}
	public String getAdminCreatorName() {
		return adminCreatorName;
	}
	public void setAdminCreatorName(String adminCreatorName) {
		this.adminCreatorName = adminCreatorName;
	}
	public String getAdminUpdaterName() {
		return adminUpdaterName;
	}
	public void setAdminUpdaterName(String adminUpdaterName) {
		this.adminUpdaterName = adminUpdaterName;
	}
}
