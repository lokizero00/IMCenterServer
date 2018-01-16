package com.loki.server.service;

import com.loki.server.dto.ServiceResult;
import com.loki.server.vo.PersonalCenterVO;

public interface PersonalCenterService {
	ServiceResult<PersonalCenterVO> getPersonalCenter(int userId);
}
