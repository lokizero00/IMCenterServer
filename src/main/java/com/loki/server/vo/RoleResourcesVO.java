package com.loki.server.vo;

import java.io.Serializable;

public class RoleResourcesVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int roleId;
	private int resourcesId;
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
	public int getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(int resourcesId) {
		this.resourcesId = resourcesId;
	}
}
