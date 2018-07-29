package com.loki.server.dto;

import java.io.Serializable;
import java.util.List;

import com.loki.server.entity.Admin;
import com.loki.server.entity.Resources;
import com.loki.server.entity.Role;

public class AdminLoginDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private AdminDTO adminDTO;
	private Role role;
	private List<Resources> menuList;
	private List<String> permissionList;
	public AdminDTO getAdminDTO() {
		return adminDTO;
	}
	public void setAdminDTO(AdminDTO adminDTO) {
		this.adminDTO = adminDTO;
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
	public List<String> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}
}
