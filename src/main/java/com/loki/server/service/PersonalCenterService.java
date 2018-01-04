package com.loki.server.service;

import java.util.HashMap;

import com.loki.server.model.EnterpriseCertification;
import com.loki.server.model.IdentityCertification;

public interface PersonalCenterService {
	HashMap<String,Object> getPersonalCenter(int userId);
	HashMap<String,Object> getUser(int userId);
	HashMap<String,Object> updateIdentityCertification(IdentityCertification identityCertification);
	HashMap<String,Object> updateEnterpriseCertification(EnterpriseCertification enterpriseCertification);
}
