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
import com.loki.server.dao.ResourcesDao;
import com.loki.server.dto.ResourcesDTO;
import com.loki.server.dto.ResourcesDTO;
import com.loki.server.dto.ResourcesDTO;
import com.loki.server.dto.convertor.ResourcesConvertor;
import com.loki.server.dto.convertor.ResourcesConvertor;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Resources;
import com.loki.server.entity.Resources;
import com.loki.server.entity.Resources;
import com.loki.server.entity.Resources;
import com.loki.server.service.ResourcesService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ResourcesVO;

@Service
@Transactional
public class ResourcesServiceImpl extends BaseService implements ResourcesService{
	@Resource
	ResourcesDao resourcesDao;

	@Override
	public boolean addResources(ResourcesVO resourcesVO) throws ServiceException {
		if(resourcesVO!=null) {
			Resources resources=new Resources();
			resources.setAdminCreatorId(resourcesVO.getAdminCreatorId());
			resources.setCreateTime(new Timestamp(System.currentTimeMillis()));
			resources.setModel(resourcesVO.getModel());
			resources.setName(resourcesVO.getName());
			resources.setUrl(resourcesVO.getUrl());
			resources.setDescription(resourcesVO.getDescription());
			resources.setType(resourcesVO.getType());
			resources.setSort(resourcesVO.getSort());
			resources.setStatus(resourcesVO.getStatus());
			resources.setPic(resourcesVO.getPic());
			resources.setParentId(resourcesVO.getParentId());
			resourcesDao.insert(resources);
			if (resources.getId()>0) {
				return true;
			}else {
				throw new ServiceException(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean editResources(ResourcesVO resourcesVO) throws ServiceException {
		if(resourcesVO!=null && resourcesVO.getId()>0) {
			Resources resources=resourcesDao.findById(resourcesVO.getId());
			if(resources!=null) {
				resources.setAdminUpdaterId(resourcesVO.getAdminUpdaterId());
				resources.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				resources.setModel(resourcesVO.getModel());
				resources.setName(resourcesVO.getName());
				resources.setUrl(resourcesVO.getUrl());
				resources.setDescription(resourcesVO.getDescription());
				resources.setType(resourcesVO.getType());
				resources.setSort(resourcesVO.getSort());
				resources.setStatus(resourcesVO.getStatus());
				resources.setPic(resourcesVO.getPic());
				resources.setParentId(resourcesVO.getParentId());
				if(resourcesDao.update(resources)) {
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
	public ResourcesDTO getResources(int id) throws ServiceException {
		if(id>0) {
			Resources resources=resourcesDao.findById(id);
			if(resources!=null) {
				ResourcesDTO resourcesDTO=ResourcesConvertor.convertResources2ResourcesDTO(resources);
				resourcesDTO.setAdminCreatorName(getAdminName(resources.getAdminCreatorId()));
				resourcesDTO.setAdminUpdaterName(getAdminName(resources.getAdminUpdaterId()));
				return resourcesDTO;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<ResourcesDTO> getResourcesList(Map<String, Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<Resources> resourcesList=resourcesDao.findByParam(map);
			if(resourcesList!=null) {
				List<ResourcesDTO> resourcesDTOList=new ArrayList<>();
				for(Resources resources:resourcesList) {
					ResourcesDTO resourcesDTO=new ResourcesDTO();
					resourcesDTO=ResourcesConvertor.convertResources2ResourcesDTO(resources);
					resourcesDTO.setAdminCreatorName(getAdminName(resources.getAdminCreatorId()));
					resourcesDTO.setAdminUpdaterName(getAdminName(resources.getAdminUpdaterId()));
					resourcesDTOList.add(resourcesDTO);
				}
				Page data=(Page) resourcesList;
				PagedResult<ResourcesDTO> pagedList=BeanUtil.toPagedResult(resourcesDTOList,data.getPageNum(),data.getPageSize(),data.getTotal(),data.getPages());
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
	public boolean delResources(int id) throws ServiceException {
		if(id>0) {
			return resourcesDao.delete(id);
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

}
