package com.loki.server.service;

import com.loki.server.dto.TradeReportDTO;
import com.loki.server.dto.TradeReportInformationDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeReportInformationVO;
import com.loki.server.vo.TradeReportVO;

public interface TradeReportService {
	ServiceResult<Integer> addTradeReport(TradeReportVO tradeReportVO);
	ServiceResult<Integer> addTradeReportInformation(TradeReportInformationVO tradeReportInformationVO);
	ServiceResult<Void> cancelTradeReport(int tradeReportId,int informerId);
	ServiceResult<PagedResult<TradeReportDTO>> getTradeReportList(int tradeId,Integer pageNo,Integer pageSize);
	ServiceResult<PagedResult<TradeReportInformationDTO>> getTradeReportInformationList(int tradeReportId,Integer pageNo,Integer pageSize);
	ServiceResult<TradeReportDTO> getTradeReport(int tradeReportId);
	
}
