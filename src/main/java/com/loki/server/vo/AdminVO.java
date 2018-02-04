package com.loki.server.vo;

import java.io.Serializable;
import java.util.List;

import com.loki.server.entity.Admin;
import com.loki.server.entity.Resources;
import com.loki.server.entity.Role;

public class AdminVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Admin admin;
	private Role role;
	private List<Resources> menuList;
	private List<Resources> permissionList;
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Resources> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Resources> menuList) {
		this.menuList = menuList;
	}
	public List<Resources> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<Resources> permissionList) {
		this.permissionList = permissionList;
	}
}
