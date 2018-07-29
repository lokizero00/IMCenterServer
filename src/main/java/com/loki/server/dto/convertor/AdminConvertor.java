package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.AdminDTO;
import com.loki.server.entity.Admin;

public class AdminConvertor {
	public static AdminDTO convertAdmin2AdminDTO(Admin admin) {
		if(admin==null) {
			return null;
		}
		AdminDTO adminDTO=new AdminDTO();
		adminDTO.setId(admin.getId());
		adminDTO.setCreateTime(admin.getCreateTime());
		adminDTO.setUpdateTime(admin.getUpdateTime());
		adminDTO.setAdminCreatorId(admin.getAdminCreatorId());
		adminDTO.setAdminUpdaterId(admin.getAdminUpdaterId());
		adminDTO.setUserName(admin.getUserName());
		adminDTO.setPassword(admin.getPassword());
		adminDTO.setLoginTime(admin.getLoginTime());
		adminDTO.setLoginCount(admin.getLoginCount());
		adminDTO.setSuperAdmin(admin.isSuperAdmin());
		adminDTO.setStatus(admin.getStatus());
		return adminDTO;
	}
	
	public static List<AdminDTO> convertAdmins2AdminDTOs(List<Admin> admins){
		List<AdminDTO> adminDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(admins)) {
			for(Admin admin:admins) {
				AdminDTO adminDTO=new AdminDTO();
				adminDTO.setId(admin.getId());
				adminDTO.setCreateTime(admin.getCreateTime());
				adminDTO.setUpdateTime(admin.getUpdateTime());
				adminDTO.setAdminCreatorId(admin.getAdminCreatorId());
				adminDTO.setAdminUpdaterId(admin.getAdminUpdaterId());
				adminDTO.setUserName(admin.getUserName());
				adminDTO.setPassword(admin.getPassword());
				adminDTO.setLoginTime(admin.getLoginTime());
				adminDTO.setLoginCount(admin.getLoginCount());
				adminDTO.setSuperAdmin(admin.isSuperAdmin());
				adminDTO.setStatus(admin.getStatus());
				adminDTOs.add(adminDTO);
			}
		}
		return adminDTOs;
	}
}
