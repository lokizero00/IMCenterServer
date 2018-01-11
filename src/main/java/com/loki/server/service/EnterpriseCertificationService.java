package com.loki.server.service;

import java.util.HashMap;

public interface EnterpriseCertificationService {
	HashMap<String,Object> updateEnterpriseCertification(int userId,String position,String enterpriseName,String licensePic);
	HashMap<String, Object> getEnterpriseCertification(int userId);
}
