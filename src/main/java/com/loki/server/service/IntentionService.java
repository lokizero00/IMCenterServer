package com.loki.server.service;


import java.util.List;

import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserBankcard;
import com.loki.server.vo.ServiceResult;

public interface IntentionService {
	ServiceResult<Intention> getIntention(int userId);
	ServiceResult<PagedResult<IntentionLog>> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo,Integer pageSize);
	ServiceResult<List<UserBankcard>> getUserBankcard(int userId);
	ServiceResult<Void> addUserBankcard(UserBankcard userBankcard);
	ServiceResult<Void> deleteUserBankcard(int userBankcardId);
}
