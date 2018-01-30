package com.loki.server.service;

import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.vo.EnterpriseCertificationVO;
import com.loki.server.vo.ServiceResult;

public interface EnterpriseCertificationService {
	ServiceResult<EnterpriseCertification> getEnterpriseCertification(int userId);
	ServiceResult<Integer> addEnterpriseCertification(EnterpriseCertificationVO enterpriseCertificationVO);
	ServiceResult<Void> editEnterpriseCertification(EnterpriseCertificationVO enterpriseCertificationVO);
}
