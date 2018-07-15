package com.loki.server.service.impl;

import java.util.List;
import java.util.Map;

import com.loki.server.dto.AdvDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.AdvService;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.AdvVO;
import com.loki.server.vo.ServiceResult;

public class AdvServiceImpl implements AdvService {

	@Override
	public ServiceResult<List<AdvDTO>> getAdvList_mobile(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult<AdvDTO> getAdv(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAdv(AdvVO advVO) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PagedResult<AdvDTO> getAdvList(Map<String, Object> map) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editAdv(AdvVO advVO) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delAdv(int id) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
