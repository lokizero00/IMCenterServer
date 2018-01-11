package com.loki.server.service;

import java.util.HashMap;

public interface IdentityCertificationService {
	HashMap<String,Object> updateIdentityCertification(int userId,String trueName,String identityNumber,String identityFront,String identityBack);
	HashMap<String,Object> getIdentityCertification(int userId);
	HashMap<String, Object> getIdentityCertificationStatus(int userId);
}
