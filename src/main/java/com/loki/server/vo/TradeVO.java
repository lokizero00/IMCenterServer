package com.loki.server.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeAttachment;
import com.loki.server.entity.TradeIndustry;
import com.loki.server.entity.TradeInvoice;
import com.loki.server.entity.TradePaycode;

public class TradeVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Trade trade;
	private List<TradeAttachment> tradeAttachmentList;
	private List<TradeIndustry> tradeIndustryList;
	private List<TradeInvoice> tradeInvoiceList;
	private List<TradePaycode> tradePaycodeList;
	private BigDecimal freezeIntention;
	private String tradeDeliveryTimeStr;
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	public List<TradeAttachment> getTradeAttachmentList() {
		return tradeAttachmentList;
	}
	public void setTradeAttachmentList(List<TradeAttachment> tradeAttachmentList) {
		this.tradeAttachmentList = tradeAttachmentList;
	}
	public List<TradeIndustry> getTradeIndustryList() {
		return tradeIndustryList;
	}
	public void setTradeIndustryList(List<TradeIndustry> tradeIndustryList) {
		this.tradeIndustryList = tradeIndustryList;
	}
	public List<TradeInvoice> getTradeInvoiceList() {
		return tradeInvoiceList;
	}
	public void setTradeInvoiceList(List<TradeInvoice> tradeInvoiceList) {
		this.tradeInvoiceList = tradeInvoiceList;
	}
	public List<TradePaycode> getTradePaycodeList() {
		return tradePaycodeList;
	}
	public void setTradePaycodeList(List<TradePaycode> tradePaycodeList) {
		this.tradePaycodeList = tradePaycodeList;
	}
	public String getTradeDeliveryTimeStr() {
		return tradeDeliveryTimeStr;
	}
	public void setTradeDeliveryTimeStr(String tradeDeliveryTimeStr) {
		this.tradeDeliveryTimeStr = tradeDeliveryTimeStr;
	}
	public BigDecimal getFreezeIntention() {
		return freezeIntention;
	}
	public void setFreezeIntention(BigDecimal freezeIntention) {
		this.freezeIntention = freezeIntention;
	}
}
