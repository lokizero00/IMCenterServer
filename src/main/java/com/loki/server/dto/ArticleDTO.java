package com.loki.server.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ArticleDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int adminCreatorId;
	private int adminUpdaterId;
	private String title;
	private String content;
	private int sort;
	private String status;
	private int readCount;
	private String adminCreatorName;
	private String adminUpdaterName;
	private String statusName;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
