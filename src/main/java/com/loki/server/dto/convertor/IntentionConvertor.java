package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.IntentionDTO;
import com.loki.server.entity.Intention;


public class IntentionConvertor {
	public static IntentionDTO convertIntention2IntentionDTO(Intention intention) {
		if(intention==null) {
			return null;
		}
		IntentionDTO intentionDTO=new IntentionDTO();
		intentionDTO.setId(intention.getId());
		intentionDTO.setTotal(intention.getTotal());
		intentionDTO.setAvailable(intention.getAvailable());
		intentionDTO.setFreeze(intention.getFreeze());
		intentionDTO.setUserId(intention.getUserId());
		return intentionDTO;
	}
	
	public static List<IntentionDTO> convertIntentions2IntentionDTOs(List<Intention> intentions){
		List<IntentionDTO> intentionDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(intentions)) {
			for(Intention intention:intentions) {
				IntentionDTO intentionDTO=new IntentionDTO();
				intentionDTO.setId(intention.getId());
				intentionDTO.setTotal(intention.getTotal());
				intentionDTO.setAvailable(intention.getAvailable());
				intentionDTO.setFreeze(intention.getFreeze());
				intentionDTO.setUserId(intention.getUserId());
				intentionDTOs.add(intentionDTO);
			}
		}
		return intentionDTOs;
	}
}
