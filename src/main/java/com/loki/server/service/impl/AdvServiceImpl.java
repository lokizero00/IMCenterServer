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
import com.loki.server.dao.AdvDao;
import com.loki.server.dto.AdvDTO;
import com.loki.server.dto.convertor.AdvConvertor;
import com.loki.server.entity.Adv;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.AdvService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.AdvVO;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class AdvServiceImpl extends BaseService implements AdvService {
	@Resource AdvDao advDao;

	@Override
	public ServiceResult<List<AdvDTO>> getAdvList_mobile(int count) {
		ServiceResult<List<AdvDTO>> returnValue=new ServiceResult<>();
		if(count>0) {
			List<Adv> advList=advDao.findByCount(count);
			List<AdvDTO> advDTOList=new ArrayList<>();
			if(advList!=null) {
				for(Adv adv:advList) {
					AdvDTO advDTO=AdvConvertor.convertAdv2AdvDTO(adv);
					advDTOList.add(advDTO);
				}
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(advDTOList);
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<AdvDTO> getAdv_mobile(int id) {
		ServiceResult<AdvDTO> returnValue=new ServiceResult<>();
		if (id>0) {
			Adv adv=advDao.findById(id);
			if(adv!=null) {
				AdvDTO advDTO=AdvConvertor.convertAdv2AdvDTO(adv);
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(advDTO);
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public boolean addAdv(AdvVO advVO) throws ServiceException {
		if(advVO!=null) {
			Adv adv=new Adv();
			adv.setAdminCreatorId(advVO.getAdminCreatorId());
			adv.setCreateTime(new Timestamp(System.currentTimeMillis()));
			adv.setPosition(advVO.getPosition());
			adv.setTitle(advVO.getTitle());
			adv.setPreviewUrl(advVO.getPreviewUrl());
			adv.setContent(advVO.getContent());
			adv.setLinkable(advVO.getLinkable());
			adv.setLinkUrl(advVO.getLinkUrl());
			adv.setStartTime(advVO.getStartTime());
			adv.setEndTime(advVO.getEndTime());
			adv.setSort(advVO.getSort());
			adv.setClickCount(0);
			adv.setState(advVO.getState());
			advDao.insert(adv);
			if (adv.getId()>0) {
				return true;
			}else {
				throw new ServiceException(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<AdvDTO> getAdvList(Map<String, Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<Adv> advList=advDao.findByParam(map);
			if(advList!=null) {
				List<AdvDTO> advDTOList=new ArrayList<>();
				for(Adv adv:advList) {
					AdvDTO advDTO=new AdvDTO();
					advDTO=AdvConvertor.convertAdv2AdvDTO(adv);
					advDTO.setAdminCreatorName(getAdminName(adv.getAdminCreatorId()));
					advDTO.setAdminUpdaterName(getAdminName(adv.getAdminUpdaterId()));
					advDTOList.add(advDTO);
				}
				Page data=(Page) advList;
				PagedResult<AdvDTO> pagedList=BeanUtil.toPagedResult(advDTOList,data.getPageNum(),data.getPageSize(),data.getTotal(),data.getPages());
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
	public boolean editAdv(AdvVO advVO) throws ServiceException {
		if(advVO!=null && advVO.getId()>0) {
			Adv adv=advDao.findById(advVO.getId());
			if(adv!=null) {
				adv.setAdminUpdaterId(advVO.getAdminUpdaterId());
				adv.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				adv.setPosition(advVO.getPosition());
				adv.setTitle(advVO.getTitle());
				if(!advVO.getPreviewUrl().isEmpty()) {
					adv.setPreviewUrl(advVO.getPreviewUrl());
				}
				adv.setContent(advVO.getContent());
				adv.setLinkable(advVO.getLinkable());
				adv.setLinkUrl(advVO.getLinkUrl());
				adv.setStartTime(advVO.getStartTime());
				adv.setEndTime(advVO.getEndTime());
				adv.setSort(advVO.getSort());
				adv.setState(advVO.getState());
				if(advDao.update(adv)) {
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
	public boolean delAdv(int id) throws ServiceException {
		if(id>0) {
			return advDao.delete(id);
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public AdvDTO getAdv(int id) throws ServiceException {
		if(id>0) {
			Adv adv=advDao.findById(id);
			if(adv!=null) {
				AdvDTO advDTO=AdvConvertor.convertAdv2AdvDTO(adv);
				return advDTO;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

}
