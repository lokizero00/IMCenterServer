package com.loki.server.service;

import com.loki.server.complexModel.ServiceReturnModel;
import com.loki.server.model.EnterpriseCertification;
import com.loki.server.model.IdentityCertification;

public interface PersonalCenterService {
	ServiceReturnModel getPersonalCenter(int userId);
	ServiceReturnModel getUser(int userId);
	ServiceReturnModel updateIdentityCertification(IdentityCertification identityCertification);
	ServiceReturnModel updateEnterpriseCertification(EnterpriseCertification enterpriseCertification);
}
