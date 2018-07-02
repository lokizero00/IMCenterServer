package com.loki.server.vo;

import java.io.Serializable;
import java.util.List;

import com.loki.server.entity.TradeReport;
import com.loki.server.entity.TradeReportAttachment;
import com.loki.server.entity.TradeReportInformation;

public class TradeReportVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private TradeReport tradeReport;
	private TradeReportInformation tradeReportInformation;
	private List<TradeReportAttachment> tradeReportAttachmentList;
	public TradeReport getTradeReport() {
		return tradeReport;
	}
	public void setTradeReport(TradeReport tradeReport) {
		this.tradeReport = tradeReport;
	}
	public TradeReportInformation getTradeReportInformation() {
		return tradeReportInformation;
	}
	public void setTradeReportInformation(TradeReportInformation tradeReportInformation) {
		this.tradeReportInformation = tradeReportInformation;
	}
	public List<TradeReportAttachment> getTradeReportAttachmentList() {
		return tradeReportAttachmentList;
	}
	public void setTradeReportAttachmentList(List<TradeReportAttachment> tradeReportAttachmentList) {
		this.tradeReportAttachmentList = tradeReportAttachmentList;
	}

}
