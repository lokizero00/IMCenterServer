package com.loki.server.service;

import com.loki.server.entity.UserExtension;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

public interface UserExtensionService {
	ServiceResult<UserExtension> getUserExtension(int userId) throws ServiceException;
}
