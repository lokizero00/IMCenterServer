package com.loki.server.vo;

import java.io.Serializable;
import java.util.List;

import com.loki.server.entity.TradeReportAttachment;
import com.loki.server.entity.TradeReportInformation;

public class TradeReportInformationVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private TradeReportInformation tradeReportInformation;
	private List<TradeReportAttachment> tradeReportAttachmentList;
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
