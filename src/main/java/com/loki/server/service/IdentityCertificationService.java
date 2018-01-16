package com.loki.server.service;

import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.IdentityCertification;

public interface IdentityCertificationService {
	ServiceResult<IdentityCertification> updateIdentityCertification(int userId,String trueName,String identityNumber,String identityFront,String identityBack);
	ServiceResult<IdentityCertification> getIdentityCertification(int userId);
	ServiceResult<String> getIdentityCertificationStatus(int userId);
}
