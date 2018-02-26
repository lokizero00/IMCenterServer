package com.loki.server.service;


import java.util.List;
import java.util.Map;

import com.loki.server.dto.IntentionDTO;
import com.loki.server.dto.IntentionLogDTO;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserBankcard;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

public interface IntentionService {
	//web
	PagedResult<IntentionDTO> getIntentionList(Map<String,Object> map) throws ServiceException;
	IntentionDTO getUserIntention(int userId) throws ServiceException;
	IntentionDTO getIntention(int intentionId) throws ServiceException;
	PagedResult<IntentionLogDTO> getIntentionLog(Map<String,Object> map);
	
	//mobile
	ServiceResult<Intention> getIntention_mobile(int userId);
	ServiceResult<PagedResult<IntentionLog>> getIntentionLog_mobile(Map<String,Object> map,Integer pageNo,Integer pageSize);
	ServiceResult<List<UserBankcard>> getUserBankcard_mobile(int userId);
	ServiceResult<Void> addUserBankcard_mobile(UserBankcard userBankcard);
	ServiceResult<Void> deleteUserBankcard_mobile(int userBankcardId);
}
