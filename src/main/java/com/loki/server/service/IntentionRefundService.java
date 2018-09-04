package com.loki.server.service;

import java.util.Map;

import com.loki.server.dto.IntentionRefundDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.utils.ServiceException;

public interface IntentionRefundService {
	
	boolean addIntentionRefund(IntentionRefundDTO intentionRefundDto) throws ServiceException;
	boolean editIntentionRefund(IntentionRefundDTO intentionRefundDto) throws ServiceException;
	IntentionRefundDTO getIntentionRefund(int id) throws ServiceException;
	PagedResult<IntentionRefundDTO> getIntentionRefundList(Map<String,Object> map) throws ServiceException;
	boolean delIntentionRefund(int id) throws ServiceException;

}
