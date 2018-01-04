package com.loki.server.model;

import java.io.Serializable;

public class AdminDept implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int adminId;
	private int deptId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
}
