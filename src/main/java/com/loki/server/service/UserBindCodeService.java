package com.loki.server.service;

import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.UserBindCode;

public interface UserBindCodeService {
	ServiceResult<UserBindCode> sendSmsAuthCode(String phone);
	ServiceResult<UserBindCode> checkAuthCode(int authCodeId,String authCode);
}
