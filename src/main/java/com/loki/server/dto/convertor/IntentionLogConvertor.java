package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.IntentionLogDTO;
import com.loki.server.entity.IntentionLog;

public class IntentionLogConvertor {
	public static IntentionLogDTO convertIntentionLog2IntentionLogDTO(IntentionLog intentionLog) {
		if(intentionLog==null) {
			return null;
		}
		IntentionLogDTO intentionLogDTO=new IntentionLogDTO();
		intentionLogDTO.setId(intentionLog.getId());
		intentionLogDTO.setCreateTime(intentionLog.getCreateTime());
		intentionLogDTO.setAvailableAmount(intentionLog.getAvailableAmount());
		intentionLogDTO.setChangeAmount(intentionLog.getChangeAmount());
		intentionLogDTO.setContent(intentionLog.getContent());
		intentionLogDTO.setIntentionId(intentionLog.getIntentionId());
		intentionLogDTO.setLogRole(intentionLog.getLogRole());
		intentionLogDTO.setLogOperatorId(intentionLog.getLogOperatorId());
		intentionLogDTO.setRelationType(intentionLog.getRelationType());
		intentionLogDTO.setRelationId(intentionLog.getRelationId());
		return intentionLogDTO;
	}
	
	public static List<IntentionLogDTO> convertIntentionLogs2IntentionLogDTOs(List<IntentionLog> intentionLogs){
		List<IntentionLogDTO> intentionLogDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(intentionLogs)) {
			for(IntentionLog intentionLog:intentionLogs) {
				IntentionLogDTO intentionLogDTO=new IntentionLogDTO();
				intentionLogDTO.setId(intentionLog.getId());
				intentionLogDTO.setCreateTime(intentionLog.getCreateTime());
				intentionLogDTO.setAvailableAmount(intentionLog.getAvailableAmount());
				intentionLogDTO.setChangeAmount(intentionLog.getChangeAmount());
				intentionLogDTO.setContent(intentionLog.getContent());
				intentionLogDTO.setIntentionId(intentionLog.getIntentionId());
				intentionLogDTO.setLogRole(intentionLog.getLogRole());
				intentionLogDTO.setLogOperatorId(intentionLog.getLogOperatorId());
				intentionLogDTO.setRelationType(intentionLog.getRelationType());
				intentionLogDTO.setRelationId(intentionLog.getRelationId());
				intentionLogDTOs.add(intentionLogDTO);
			}
		}
		return intentionLogDTOs;
	}
}
