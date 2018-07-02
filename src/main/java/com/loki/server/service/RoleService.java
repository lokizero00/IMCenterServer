package com.loki.server.service;

import com.loki.server.utils.ServiceException;
import com.loki.server.vo.RoleVO;

public interface RoleService {
	void addRole(RoleVO roleVO) throws ServiceException;
	boolean editRole(RoleVO role) throws ServiceException;
	RoleVO getRole(int id) throws ServiceException;
//	List<Role> getRoleList() throws ServiceException;
}
