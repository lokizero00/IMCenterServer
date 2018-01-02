package com.loki.server.service;

import com.loki.server.complexModel.ServiceReturnModel;

public interface PersonalCenterService {
	ServiceReturnModel getPersonalData(int userId);
	ServiceReturnModel getUser(int userId);
}
