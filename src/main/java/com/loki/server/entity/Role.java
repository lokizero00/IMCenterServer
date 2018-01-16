package com.loki.server.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int adminCreatorId;
	private int adminUpdaterId;
	private String name;
	private String description;
	private int sort;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}
