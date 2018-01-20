package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.DictionariesDao;
import com.loki.server.dao.TradeAttachmentDao;
import com.loki.server.dao.TradeDao;
import com.loki.server.dao.TradeIndustryDao;
import com.loki.server.dao.TradeInvoiceDao;
import com.loki.server.dao.TradePaycodeDao;
import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeAttachment;
import com.loki.server.entity.TradeIndustry;
import com.loki.server.entity.TradeInvoice;
import com.loki.server.entity.TradePaycode;
import com.loki.server.service.TradeService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

@Service
@Transactional
public class TradeServiceImpl implements TradeService {
	@Resource TradeDao tradeDao;
	@Resource TradeAttachmentDao tradeAttachmentDao;
	@Resource TradeIndustryDao tradeIndustryDao;
	@Resource TradeInvoiceDao tradeInvoiceDao;
	@Resource TradePaycodeDao tradePaycodeDao;
	@Resource DictionariesDao dictionariesDao;
	
	@Override
	public ServiceResult<Integer> publishTrade(TradeVO tradeVO) {
		ServiceResult<Integer> returnValue=new ServiceResult<Integer>();
		if(tradeVO.getTrade()!=null) {
			Trade trade=tradeVO.getTrade();
			if(trade.getType().equals("trade_demand")) {
				trade.setDeliveryTime(CommonUtil.getInstance().convert(tradeVO.getTradeDeliveryTimeStr()));
			}
			trade.setStatus("trade_verify");
			List<TradeAttachment> tradeAttachmentList=tradeVO.getTradeAttachmentList();
			List<TradeIndustry> tradeIndustryList=tradeVO.getTradeIndustryList();
			List<TradeInvoice> tradeInvoiceList=tradeVO.getTradeInvoiceList();
			List<TradePaycode> tradePaycodeList=tradeVO.getTradePaycodeList();
			trade.setCreatorId(trade.getUserId());
			trade.setSn(CommonUtil.getInstance().getTradeSN(trade.getType()));
			tradeDao.insert(trade);
			if(trade.getId()>0) {
				if(tradeAttachmentList!=null) {
					for(TradeAttachment tradeAttachment:tradeAttachmentList) {
						tradeAttachment.setTradeId(trade.getId());
						tradeAttachmentDao.insert(tradeAttachment);
					}
				}
				if(tradeIndustryList!=null) {
					for(TradeIndustry tradeIndustry:tradeIndustryList) {
						tradeIndustry.setTradeId(trade.getId());
						tradeIndustry.setValue(dictionariesDao.findValueByParam("industry", tradeIndustry.getCode()));
						tradeIndustryDao.insert(tradeIndustry);
					}
				}
				if(tradeInvoiceList!=null) {
					for(TradeInvoice tradeInvoice:tradeInvoiceList) {
						tradeInvoice.setTradeId(trade.getId());
						tradeInvoice.setValue(dictionariesDao.findValueByParam("invoice", tradeInvoice.getCode()));
						tradeInvoiceDao.insert(tradeInvoice);
					}
				}
				if(tradePaycodeList!=null) {
					for(TradePaycode tradePaycode:tradePaycodeList) {
						tradePaycode.setTradeId(trade.getId());
						tradePaycode.setValue(dictionariesDao.findValueByParam("paycode", tradePaycode.getCode()));
						tradePaycodeDao.insert(tradePaycode);
					}
				}
				
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(trade.getId());
			}else {
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		}
		else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

}
