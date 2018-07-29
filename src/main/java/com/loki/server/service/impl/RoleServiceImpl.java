package com.loki.server.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.loki.server.dao.RoleDao;
import com.loki.server.dao.RoleResourcesDao;
import com.loki.server.dto.RoleDTO;
import com.loki.server.dto.convertor.RoleConvertor;
import com.loki.server.entity.Role;
import com.loki.server.entity.RoleResources;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.RoleService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.RoleResourcesVO;
import com.loki.server.vo.RoleVO;

import net.sf.json.JSONArray;

@Service
@Transactional
public class RoleServiceImpl extends BaseService implements RoleService{
	@Resource 
	RoleDao roleDao;
	@Resource
	RoleResourcesDao roleResourcesDao;

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
	public boolean editRole(RoleVO roleVO) throws ServiceException {
		if(roleVO!=null && roleVO.getId()>0) {
			Role role=roleDao.findById(roleVO.getId());
			if(role!=null) {
				role.setAdminUpdaterId(roleVO.getAdminUpdaterId());
				role.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				role.setName(roleVO.getName());
				role.setDescription(roleVO.getDescription());
				role.setSort(roleVO.getSort());
				if(roleDao.update(role)) {
					return true;
				}else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}
	
	@Override
	public boolean delRole(int id) throws ServiceException {
		if(id>0) {
			//解除角色与资源的绑定
			if(roleResourcesDao.deleteByRoleId(id)) {
				return roleDao.delete(id);
			}else {
				throw new ServiceException(ResultCodeEnums.DELETE_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public RoleDTO getRole(int id) throws ServiceException {
		if(id>0) {
			Role role=roleDao.findById(id);
			if(role!=null) {
				RoleDTO roleDTO=RoleConvertor.convertRole2RoleDTO(role);
				roleDTO.setAdminCreatorName(getAdminName(role.getAdminCreatorId()));
				roleDTO.setAdminUpdaterName(getAdminName(role.getAdminUpdaterId()));
				return roleDTO;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<RoleDTO> getRoleList(Map<String,Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<Role> roleList=roleDao.findByParam(map);
			if(roleList!=null) {
				List<RoleDTO> roleDTOList=new ArrayList<>();
				for(Role role:roleList) {
					RoleDTO roleDTO=new RoleDTO();
					roleDTO=RoleConvertor.convertRole2RoleDTO(role);
					roleDTO.setAdminCreatorName(getAdminName(role.getAdminCreatorId()));
					roleDTO.setAdminUpdaterName(getAdminName(role.getAdminUpdaterId()));
					roleDTOList.add(roleDTO);
				}
				Page data=(Page) roleList;
				PagedResult<RoleDTO> pagedList=BeanUtil.toPagedResult(roleDTOList,data.getPageNum(),data.getPageSize(),data.getTotal(),data.getPages());
				if(pagedList!=null) {
					return pagedList;
				}else {
					throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean authRole(String authJson) throws ServiceException {
		if(authJson!=null && authJson!="") {
			JSONArray json = JSONArray.fromObject(authJson);
			List<RoleResourcesVO> authList= (List<RoleResourcesVO>)JSONArray.toCollection(json, RoleResourcesVO.class);
			if(authList.size()>0) {
				int roleId=authList.get(0).getRoleId();
				roleResourcesDao.deleteByRoleId(roleId);
			}
			for(RoleResourcesVO roleResourcesVO:authList) {
				RoleResources roleResources=new RoleResources();
				roleResources.setResourcesId(roleResourcesVO.getResourcesId());
				roleResources.setRoleId(roleResourcesVO.getRoleId());
				roleResourcesDao.insert(roleResources);
			}
			return true;
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}
}
