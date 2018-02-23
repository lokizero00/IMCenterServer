package com.loki.server.service;

import java.util.Map;

import com.loki.server.dto.EnterpriseCertificationDTO;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.PagedResult;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.EnterpriseCertificationVO;
import com.loki.server.vo.ServiceResult;

public interface EnterpriseCertificationService {
	//web
	PagedResult<EnterpriseCertificationDTO> getEnterpriseCertificationList(Map<String,Object> map) throws ServiceException;
	
	//mobile
	ServiceResult<EnterpriseCertification> getEnterpriseCertification_mobile(int userId);
	ServiceResult<Integer> addEnterpriseCertification_mobile(EnterpriseCertificationVO enterpriseCertificationVO);
	ServiceResult<Void> editEnterpriseCertification_mobile(EnterpriseCertificationVO enterpriseCertificationVO);
}
