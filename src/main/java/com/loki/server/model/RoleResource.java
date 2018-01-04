package com.loki.server.model;

import java.io.Serializable;

public class RoleResource implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int roleId;
	private int resourceId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
}
