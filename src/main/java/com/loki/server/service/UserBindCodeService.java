package com.loki.server.service;

import com.loki.server.vo.ServiceResult;

public interface UserBindCodeService {
	ServiceResult<Integer> sendSmsAuthCode(String phone);
	ServiceResult<Void> checkAuthCode(int authCodeId,String authCode);
}
