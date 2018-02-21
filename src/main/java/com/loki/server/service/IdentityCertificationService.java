package com.loki.server.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.loki.server.dto.IdentityCertificationDTO;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.PagedResult;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.IdentityCertificationVO;
import com.loki.server.vo.ServiceResult;

public interface IdentityCertificationService {
	//web
	PagedResult<IdentityCertificationDTO> getIdentityCertificationList(Map<String,Object> map) throws ServiceException;
	IdentityCertificationDTO getIdentityCertification(HttpServletRequest request,int id) throws ServiceException;
	boolean verifyIdentityCertification(HttpServletRequest request,int id,String verify,String refuseReason) throws ServiceException;
	
	//mobile
	ServiceResult<Integer> addIdentityCertification_mobile(IdentityCertificationVO identityCertificationVO);
	ServiceResult<Void> editIdentityCertification_mobile(IdentityCertificationVO identityCertificationVO);
	ServiceResult<IdentityCertification> getIdentityCertification_mobile(int userId);
	ServiceResult<String> getIdentityCertificationStatus_mobile(int userId);
}
