package com.loki.server.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import com.loki.server.dao.RoleDao;
import com.loki.server.dto.RoleDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Role;
import com.loki.server.service.RoleService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.RoleVO;

public class RoleServiceImpl implements RoleService{
	@Resource 
	RoleDao roleDao;

	@Override
	public boolean addRole(RoleVO roleVO) throws ServiceException {
		if(roleVO!=null) {
			Role role=new Role();
			role.setAdminCreatorId(roleVO.getAdminCreatorId());
			role.setCreateTime(new Timestamp(System.currentTimeMillis()));
			role.setName(roleVO.getName());
			role.setDescription(roleVO.getDescription());
			role.setSort(roleVO.getSort());
			roleDao.insert(role);
			if (role.getId()>0) {
				return true;
			}else {
				throw new ServiceException(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean editRole(RoleVO role) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RoleDTO getRole(int id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagedResult<RoleDTO> getRoleList() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}


}
