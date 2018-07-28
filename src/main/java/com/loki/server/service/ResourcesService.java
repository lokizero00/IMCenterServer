package com.loki.server.service;

import java.util.Map;

import com.loki.server.dto.ResourcesDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ResourcesVO;

public interface ResourcesService {
	boolean addResources(ResourcesVO resourcesVO) throws ServiceException;
	boolean editResources(ResourcesVO resources) throws ServiceException;
	ResourcesDTO getResources(int id) throws ServiceException;
	PagedResult<ResourcesDTO> getResourcesList(Map<String,Object> map) throws ServiceException;
	boolean delResources(int id) throws ServiceException;
}
