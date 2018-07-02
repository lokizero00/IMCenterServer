package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.TradeReportInformationDTO;
import com.loki.server.entity.TradeReportInformation;

public class TradeReportInformationConvertor {
	public static TradeReportInformationDTO convertTradeReportInformation2TradeReportInformationDTO(TradeReportInformation tradeReportInformation) {
		if(tradeReportInformation==null) {
			return null;
		}
		TradeReportInformationDTO tradeReportInformationDTO=new TradeReportInformationDTO();
		tradeReportInformationDTO.setId(tradeReportInformation.getId());
		tradeReportInformationDTO.setCreateTime(tradeReportInformation.getCreateTime());
		tradeReportInformationDTO.setUpdateTime(tradeReportInformation.getUpdateTime());
		tradeReportInformationDTO.setCreatorId(tradeReportInformation.getCreatorId());
		tradeReportInformationDTO.setUpdaterId(tradeReportInformation.getUpdaterId());
		tradeReportInformationDTO.setTradeReportId(tradeReportInformation.getTradeReportId());
		tradeReportInformationDTO.setTradeId(tradeReportInformation.getTradeId());
		tradeReportInformationDTO.setType(tradeReportInformation.getType());
		tradeReportInformationDTO.setContent(tradeReportInformation.getContent());
		return tradeReportInformationDTO;
	}
	
	public static List<TradeReportInformationDTO> convertTradeReportInformations2TradeReportInformationDTOs(List<TradeReportInformation> tradeReportInformations){
		List<TradeReportInformationDTO> tradeReportInformationDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(tradeReportInformations)) {
			for(TradeReportInformation tradeReportInformation:tradeReportInformations) {
				TradeReportInformationDTO tradeReportInformationDTO=new TradeReportInformationDTO();
				tradeReportInformationDTO.setId(tradeReportInformation.getId());
				tradeReportInformationDTO.setCreateTime(tradeReportInformation.getCreateTime());
				tradeReportInformationDTO.setUpdateTime(tradeReportInformation.getUpdateTime());
				tradeReportInformationDTO.setCreatorId(tradeReportInformation.getCreatorId());
				tradeReportInformationDTO.setUpdaterId(tradeReportInformation.getUpdaterId());
				tradeReportInformationDTO.setTradeReportId(tradeReportInformation.getTradeReportId());
				tradeReportInformationDTO.setTradeId(tradeReportInformation.getTradeId());
				tradeReportInformationDTO.setType(tradeReportInformation.getType());
				tradeReportInformationDTO.setContent(tradeReportInformation.getContent());
				tradeReportInformationDTOs.add(tradeReportInformationDTO);
			}
		}
		return tradeReportInformationDTOs;
	}
}
