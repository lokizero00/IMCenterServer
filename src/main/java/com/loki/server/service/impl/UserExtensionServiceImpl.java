package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.UserExtensionDao;
import com.loki.server.entity.UserExtension;
import com.loki.server.service.UserExtensionService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class UserExtensionServiceImpl implements UserExtensionService{
	@Resource UserExtensionDao userExtensionDao;

	@Override
	public ServiceResult<UserExtension> getUserExtension(int userId) throws ServiceException {
		ServiceResult<UserExtension> returnValue=new ServiceResult<>();
		try {
			if(userId>0) {
				UserExtension ue=userExtensionDao.findByUserId(userId);
				if(ue!=null) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(ue);
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		}catch(Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		
		return returnValue;
	}

}
