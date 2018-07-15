package com.loki.server.service;

import java.util.List;
import java.util.Map;

import com.loki.server.dto.AdvDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.AdvVO;
import com.loki.server.vo.ServiceResult;

public interface AdvService {
	//mobile
	ServiceResult<List<AdvDTO>> getAdvList_mobile(int count);
	ServiceResult<AdvDTO> getAdv(int id);
	
	//web
	boolean addAdv(AdvVO advVO) throws ServiceException; 
	PagedResult<AdvDTO> getAdvList(Map<String, Object> map) throws ServiceException;
	boolean editAdv(AdvVO advVO) throws ServiceException;
	boolean delAdv(int id) throws ServiceException;
}
