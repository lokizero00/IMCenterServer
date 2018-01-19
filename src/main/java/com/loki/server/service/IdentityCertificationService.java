package com.loki.server.service;

import com.loki.server.entity.IdentityCertification;
import com.loki.server.vo.ServiceResult;

public interface IdentityCertificationService {
	ServiceResult<IdentityCertification> updateIdentityCertification(int userId,String trueName,String identityNumber,String identityFront,String identityBack);
	ServiceResult<IdentityCertification> getIdentityCertification(int userId);
	ServiceResult<String> getIdentityCertificationStatus(int userId);
}
