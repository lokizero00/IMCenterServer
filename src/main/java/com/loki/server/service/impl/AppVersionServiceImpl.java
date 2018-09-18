package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.AppVersionDao;
import com.loki.server.entity.AppVersion;
import com.loki.server.service.AppVersionService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	AppVersionDao appVersionDao;

	@Override
	public ServiceResult<AppVersion> getAppVersion(String appId) {
		ServiceResult<AppVersion> returnValue=new ServiceResult<>();
		if(appId!=null && appId!="") {
			try {
			AppVersion appVersion=appVersionDao.findByAppId(appId);
			if(appVersion!=null) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(appVersion);
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

}
