package com.loki.server.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.DictionariesDao;
import com.loki.server.dao.IntentionLogDao;
import com.loki.server.dao.TradeLogDao;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.TradeLog;

@Service
@Transactional
public class BaseServiceImpl {
	@Resource TradeLogDao tradeLogDao;
	@Resource IntentionLogDao intentionLogDao;
	@Resource DictionariesDao dictionariesDao;
	
	protected void addTradeLog(int tradeId,String logRole,int logOperatorId,String logState,String logContent) {
		TradeLog tradeLog=new TradeLog();
		tradeLog.setTradeId(tradeId);
		tradeLog.setLogRole(logRole);
		tradeLog.setLogOperatorId(logOperatorId);
		tradeLog.setLogState(logState);
		tradeLog.setContent(logContent);
		tradeLogDao.insert(tradeLog);
	}
	
	protected void addIntentionLog(int intentionId,BigDecimal availableAmount,BigDecimal changeAmount,String logType,int relationId,String logRole,int logOperatorId,String logContent) {
		IntentionLog intentionLog=new IntentionLog();
		intentionLog.setIntentionId(intentionId);
		intentionLog.setAvailableAmount(availableAmount);
		intentionLog.setChangeAmount(changeAmount);
		intentionLog.setContent(logContent);
		intentionLog.setRelationType(logType);
		intentionLog.setRelationId(relationId);
		intentionLog.setLogRole(logRole);
		intentionLog.setLogOperatorId(logOperatorId);
		intentionLogDao.insert(intentionLog);
	}
	
	protected String getDictionariesValue(String type,String code) {
		return dictionariesDao.findValueByParam(type, code);
	}
}