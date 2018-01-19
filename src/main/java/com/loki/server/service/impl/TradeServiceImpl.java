package com.loki.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeAttachment;
import com.loki.server.entity.TradeIndustry;
import com.loki.server.entity.TradeInvoice;
import com.loki.server.entity.TradePaycode;
import com.loki.server.service.TradeService;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class TradeServiceImpl implements TradeService {

	@Override
	public ServiceResult<Void> publishTrade(Trade trade, List<TradeAttachment> tradeAttachmentlist,
			List<TradeIndustry> tradeIndustryList, List<TradeInvoice> tradeInvoiceList,
			List<TradePaycode> tradePaycodeList) {
		// TODO Auto-generated method stub
		return null;
	}

}
