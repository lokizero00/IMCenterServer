package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.TradeDockingDTO;
import com.loki.server.entity.TradeDocking;

public class TradeDockingConvertor {
	public static TradeDockingDTO convertTradeDocking2TradeDockingDTO(TradeDocking tradeDocking) {
		if (tradeDocking == null) {
			return null;
		}
		TradeDockingDTO tradeDockingDTO = new TradeDockingDTO();
		tradeDockingDTO.setId(tradeDocking.getId());
		tradeDockingDTO.setCreateTime(tradeDocking.getCreateTime());
		tradeDockingDTO.setUpdateTime(tradeDocking.getUpdateTime());
		tradeDockingDTO.setCreatorId(tradeDocking.getCreatorId());
		tradeDockingDTO.setUpdaterId(tradeDocking.getUpdaterId());
		tradeDockingDTO.setUserId(tradeDocking.getUserId());
		tradeDockingDTO.setTradeId(tradeDocking.getTradeId());
		tradeDockingDTO.setIdentityId(tradeDocking.getIdentityId());
		tradeDockingDTO.setEnterpriseId(tradeDocking.getEnterpriseId());
		tradeDockingDTO.setOffer(tradeDocking.getOffer());
		tradeDockingDTO.setIntention(tradeDocking.getIntention());
		tradeDockingDTO.setMessage(tradeDocking.getMessage());
		tradeDockingDTO.setType(tradeDocking.getType());
		return tradeDockingDTO;
	}

	public static List<TradeDockingDTO> convertTradeDockings2TradeDockingDTOs(List<TradeDocking> tradeDockings) {
		List<TradeDockingDTO> tradeDockingDTOs = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(tradeDockings)) {
			for (TradeDocking tradeDocking : tradeDockings) {
				TradeDockingDTO tradeDockingDTO = new TradeDockingDTO();
				tradeDockingDTO.setId(tradeDocking.getId());
				tradeDockingDTO.setCreateTime(tradeDocking.getCreateTime());
				tradeDockingDTO.setUpdateTime(tradeDocking.getUpdateTime());
				tradeDockingDTO.setCreatorId(tradeDocking.getCreatorId());
				tradeDockingDTO.setUpdaterId(tradeDocking.getUpdaterId());
				tradeDockingDTO.setUserId(tradeDocking.getUserId());
				tradeDockingDTO.setTradeId(tradeDocking.getTradeId());
				tradeDockingDTO.setIdentityId(tradeDocking.getIdentityId());
				tradeDockingDTO.setEnterpriseId(tradeDocking.getEnterpriseId());
				tradeDockingDTO.setOffer(tradeDocking.getOffer());
				tradeDockingDTO.setIntention(tradeDocking.getIntention());
				tradeDockingDTO.setMessage(tradeDocking.getMessage());
				tradeDockingDTO.setType(tradeDocking.getType());
				tradeDockingDTOs.add(tradeDockingDTO);
			}
		}
		return tradeDockingDTOs;
	}
}
