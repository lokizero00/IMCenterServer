package com.loki.server.service;

import java.util.Map;

import com.loki.server.dto.IntentionRechargeDTO;
import com.loki.server.dto.RechargeDTO;
import com.loki.server.entity.IntentionJournal;
import com.loki.server.entity.PagedResult;
import com.loki.server.vo.ServiceResult;

public interface PayService {
	public ServiceResult<RechargeDTO> intentionRecharge(IntentionRechargeDTO intentionRechargeDTO,String ip);
	public ServiceResult<PagedResult<IntentionJournal>> getIntentionJournal(Map<String,Object> map);
}
