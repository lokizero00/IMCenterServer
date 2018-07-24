package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.RoleDTO;
import com.loki.server.entity.Role;

public class RoleConvertor {
	public static RoleDTO convertRole2RoleDTO(Role role) {
		if(role==null) {
			return null;
		}
		RoleDTO roleDTO=new RoleDTO();
		roleDTO.setId(role.getId());
		roleDTO.setCreateTime(role.getCreateTime());
		roleDTO.setUpdateTime(role.getUpdateTime());
		roleDTO.setAdminCreatorId(role.getAdminCreatorId());
		roleDTO.setAdminUpdaterId(role.getAdminUpdaterId());
		roleDTO.setName(role.getName());
		roleDTO.setDescription(role.getDescription());
		roleDTO.setSort(role.getSort());
		return roleDTO;
	}
	
	public static List<RoleDTO> convertRoles2RoleDTOs(List<Role> roles){
		List<RoleDTO> roleDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(roles)) {
			for(Role role:roles) {
				RoleDTO roleDTO=new RoleDTO();
				roleDTO.setId(role.getId());
				roleDTO.setCreateTime(role.getCreateTime());
				roleDTO.setUpdateTime(role.getUpdateTime());
				roleDTO.setAdminCreatorId(role.getAdminCreatorId());
				roleDTO.setAdminUpdaterId(role.getAdminUpdaterId());
				roleDTO.setName(role.getName());
				roleDTO.setDescription(role.getDescription());
				roleDTO.setSort(role.getSort());
				roleDTOs.add(roleDTO);
			}
		}
		return roleDTOs;
	}
}
