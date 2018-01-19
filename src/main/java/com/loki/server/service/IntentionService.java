package com.loki.server.service;


import java.util.List;

import com.loki.server.entity.PagedResult;
import com.loki.server.vo.IntentionLogVO;
import com.loki.server.vo.IntentionVO;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserBankcardVO;

public interface IntentionService {
	ServiceResult<IntentionVO> getIntention(int userId);
	ServiceResult<PagedResult<IntentionLogVO>> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo,Integer pageSize);
	ServiceResult<List<UserBankcardVO>> getUserBankcard(int userId);
	ServiceResult<Void> addUserBankcard(UserBankcardVO userBankcardVO);
	ServiceResult<Void> deleteUserBankcard(int userBankcardId);
}
