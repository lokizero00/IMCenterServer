package com.loki.server.service;


import com.loki.server.entity.AppVersion;
import com.loki.server.vo.ServiceResult;

public interface AppVersionService {
	//mobile
	ServiceResult<AppVersion> getAppVersion(String appId);
}
