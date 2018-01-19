package com.loki.server.service;

import com.loki.server.vo.PersonalCenterVO;
import com.loki.server.vo.ServiceResult;

public interface PersonalCenterService {
	ServiceResult<PersonalCenterVO> getPersonalCenter(int userId);
}
