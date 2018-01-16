package com.loki.server.service;


import java.util.List;

import com.loki.server.dto.PagedResult;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.UserBankcard;

public interface IntentionService {
	ServiceResult<Intention> getIntention(int userId);
	ServiceResult<PagedResult<IntentionLog>> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo,Integer pageSize);
	ServiceResult<List<UserBankcard>> getUserBankcard(int userId);
}
