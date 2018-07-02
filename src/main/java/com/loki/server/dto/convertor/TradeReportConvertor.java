package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.TradeReportDTO;
import com.loki.server.entity.TradeReport;

public class TradeReportConvertor {
	public static TradeReportDTO convertTradeReport2TradeReportDTO(TradeReport tradeReport) {
		if(tradeReport==null) {
			return null;
		}
		TradeReportDTO tradeReportDTO=new TradeReportDTO();
		tradeReportDTO.setId(tradeReport.getId());
		tradeReportDTO.setCreateTime(tradeReport.getCreateTime());
		tradeReportDTO.setUpdateTime(tradeReport.getUpdateTime());
		tradeReportDTO.setCreatorId(tradeReport.getCreatorId());
		tradeReportDTO.setTradeId(tradeReport.getTradeId());
		tradeReportDTO.setInformerId(tradeReport.getInformerId());
		tradeReportDTO.setDelinquentId(tradeReport.getDelinquentId());
		tradeReportDTO.setStatus(tradeReport.getStatus());
		tradeReportDTO.setVerifyTime(tradeReport.getVerifyTime());
		tradeReportDTO.setAdminVerifierId(tradeReport.getAdminVerifierId());
		tradeReportDTO.setTradeOwner(tradeReport.isTradeOwner());
		return tradeReportDTO;
	}
	
	public static List<TradeReportDTO> convertTradeReports2TradeReportDTOs(List<TradeReport> tradeReports){
		List<TradeReportDTO> tradeReportDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(tradeReports)) {
			for(TradeReport tradeReport:tradeReports) {
				TradeReportDTO tradeReportDTO=new TradeReportDTO();
				tradeReportDTO.setId(tradeReport.getId());
				tradeReportDTO.setCreateTime(tradeReport.getCreateTime());
				tradeReportDTO.setUpdateTime(tradeReport.getUpdateTime());
				tradeReportDTO.setCreatorId(tradeReport.getCreatorId());
				tradeReportDTO.setTradeId(tradeReport.getTradeId());
				tradeReportDTO.setInformerId(tradeReport.getInformerId());
				tradeReportDTO.setDelinquentId(tradeReport.getDelinquentId());
				tradeReportDTO.setStatus(tradeReport.getStatus());
				tradeReportDTO.setVerifyTime(tradeReport.getVerifyTime());
				tradeReportDTO.setAdminVerifierId(tradeReport.getAdminVerifierId());
				tradeReportDTO.setTradeOwner(tradeReport.isTradeOwner());
				tradeReportDTOs.add(tradeReportDTO);
			}
		}
		return tradeReportDTOs;
	}
}
