package com.loki.server.service;

import com.loki.server.entity.IdentityCertification;
import com.loki.server.vo.IdentityCertificationVO;
import com.loki.server.vo.ServiceResult;

public interface IdentityCertificationService {
	ServiceResult<Integer> addIdentityCertification(IdentityCertificationVO identityCertificationVO);
	ServiceResult<Void> editIdentityCertification(IdentityCertificationVO identityCertificationVO);
	ServiceResult<IdentityCertification> getIdentityCertification(int userId);
	ServiceResult<String> getIdentityCertificationStatus(int userId);
}
