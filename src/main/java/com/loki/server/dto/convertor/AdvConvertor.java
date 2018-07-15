package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.AdvDTO;
import com.loki.server.entity.Adv;

public class AdvConvertor {
	public static AdvDTO convertAdv2AdvDTO(Adv adv) {
		if(adv==null) {
			return null;
		}
		AdvDTO advDTO=new AdvDTO();
		advDTO.setId(adv.getId());
		advDTO.setCreateTime(adv.getCreateTime());
		advDTO.setUpdateTime(adv.getUpdateTime());
		advDTO.setAdminCreatorId(adv.getAdminCreatorId());
		advDTO.setAdminUpdaterId(adv.getAdminUpdaterId());
		advDTO.setPosition(adv.getPosition());
		advDTO.setTitle(adv.getTitle());
		advDTO.setPreviewUrl(adv.getPreviewUrl());
		advDTO.setContent(adv.getContent());
		advDTO.setLinkable(adv.getLinkable());
		advDTO.setLinkUrl(adv.getLinkUrl());
		advDTO.setStartTime(adv.getStartTime());
		advDTO.setEndTime(adv.getEndTime());
		advDTO.setSort(adv.getSort());
		advDTO.setClickCount(adv.getClickCount());
		advDTO.setState(adv.getState());
		return advDTO;
	}
	
	public static List<AdvDTO> convertAdvs2AdvDTOs(List<Adv> advs){
		List<AdvDTO> advDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(advs)) {
			for(Adv adv:advs) {
				AdvDTO advDTO=new AdvDTO();
				advDTO.setId(adv.getId());
				advDTO.setCreateTime(adv.getCreateTime());
				advDTO.setUpdateTime(adv.getUpdateTime());
				advDTO.setAdminCreatorId(adv.getAdminCreatorId());
				advDTO.setAdminUpdaterId(adv.getAdminUpdaterId());
				advDTO.setPosition(adv.getPosition());
				advDTO.setTitle(adv.getTitle());
				advDTO.setPreviewUrl(adv.getPreviewUrl());
				advDTO.setContent(adv.getContent());
				advDTO.setLinkable(adv.getLinkable());
				advDTO.setLinkUrl(adv.getLinkUrl());
				advDTO.setStartTime(adv.getStartTime());
				advDTO.setEndTime(adv.getEndTime());
				advDTO.setSort(adv.getSort());
				advDTO.setClickCount(adv.getClickCount());
				advDTO.setState(adv.getState());
				advDTOs.add(advDTO);
			}
		}
		return advDTOs;
	}
}
