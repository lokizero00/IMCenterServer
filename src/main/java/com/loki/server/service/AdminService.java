package com.loki.server.service;

import java.util.Map;

import com.loki.server.dto.AdminDTO;
import com.loki.server.dto.AdminLoginDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.AdminVO;


public interface AdminService {
	boolean add(AdminVO adminVO) throws ServiceException;;
	boolean edit(AdminVO adminVO) throws ServiceException;;
	boolean delete(int id) throws ServiceException;;
	AdminDTO getAdmin(int id) throws ServiceException;;
	PagedResult<AdminDTO> getAdminList(Map<String,Object> map) throws ServiceException;;
	AdminLoginDTO login(String userName, String password,String clientIP,String contextPath) throws ServiceException;
}
