package com.loki.server.service;


import com.loki.server.dto.PagedResult;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;

public interface IntentionService {
	ServiceResult<Intention> getIntention(int userId);
	ServiceResult<PagedResult<IntentionLog>> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo,Integer pageSize);
}
