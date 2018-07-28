package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.ResourcesDTO;
import com.loki.server.entity.Resources;

public class ResourcesConvertor {
	public static ResourcesDTO convertResources2ResourcesDTO(Resources resources) {
		if(resources==null) {
			return null;
		}
		ResourcesDTO resourcesDTO=new ResourcesDTO();
		resourcesDTO.setId(resources.getId());
		resourcesDTO.setCreateTime(resources.getCreateTime());
		resourcesDTO.setUpdateTime(resources.getUpdateTime());
		resourcesDTO.setAdminCreatorId(resources.getAdminCreatorId());
		resourcesDTO.setAdminUpdaterId(resources.getAdminUpdaterId());
		resourcesDTO.setName(resources.getName());
		resourcesDTO.setModel(resources.getModel());
		resourcesDTO.setUrl(resources.getUrl());
		resourcesDTO.setDescription(resources.getDescription());
		resourcesDTO.setType(resources.getType());
		resourcesDTO.setSort(resources.getSort());
		resourcesDTO.setStatus(resources.getStatus());
		resourcesDTO.setPic(resources.getPic());
		resourcesDTO.setParentId(resources.getParentId());
		return resourcesDTO;
	}
	
	public static List<ResourcesDTO> convertResourcess2ResourcesDTOs(List<Resources> resourcess){
		List<ResourcesDTO> resourcesDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(resourcess)) {
			for(Resources resources:resourcess) {
				ResourcesDTO resourcesDTO=new ResourcesDTO();
				resourcesDTO.setId(resources.getId());
				resourcesDTO.setCreateTime(resources.getCreateTime());
				resourcesDTO.setUpdateTime(resources.getUpdateTime());
				resourcesDTO.setAdminCreatorId(resources.getAdminCreatorId());
				resourcesDTO.setAdminUpdaterId(resources.getAdminUpdaterId());
				resourcesDTO.setName(resources.getName());
				resourcesDTO.setModel(resources.getModel());
				resourcesDTO.setUrl(resources.getUrl());
				resourcesDTO.setDescription(resources.getDescription());
				resourcesDTO.setType(resources.getType());
				resourcesDTO.setSort(resources.getSort());
				resourcesDTO.setStatus(resources.getStatus());
				resourcesDTO.setPic(resources.getPic());
				resourcesDTO.setParentId(resources.getParentId());
				resourcesDTOs.add(resourcesDTO);
			}
		}
		return resourcesDTOs;
	}
}
