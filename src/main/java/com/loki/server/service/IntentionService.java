package com.loki.server.service;

import java.util.HashMap;

public interface IntentionService {
//	HashMap<String,Object> recharge()
	HashMap<String,Object> getIntention(int userId);
	HashMap<String,Object> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo,Integer pageSize);
}
