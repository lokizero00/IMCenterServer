package com.loki.server.service;

import java.util.List;

import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeAttachment;
import com.loki.server.entity.TradeIndustry;
import com.loki.server.entity.TradeInvoice;
import com.loki.server.entity.TradePaycode;
import com.loki.server.vo.ServiceResult;

public interface TradeService {
	ServiceResult<Void> publishTrade(Trade trade,List<TradeAttachment> tradeAttachmentlist,List<TradeIndustry> tradeIndustryList,List<TradeInvoice> tradeInvoiceList,List<TradePaycode> tradePaycodeList);
}
