package com.loki.server.service;

import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.EnterpriseCertification;

public interface EnterpriseCertificationService {
	ServiceResult<EnterpriseCertification> updateEnterpriseCertification(int userId,String position,String enterpriseName,String licensePic);
	ServiceResult<EnterpriseCertification> getEnterpriseCertification(int userId);
}
