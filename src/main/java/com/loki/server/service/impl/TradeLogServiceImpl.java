package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.TradeLogDao;
import com.loki.server.entity.TradeLog;
import com.loki.server.service.TradeLogService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class TradeLogServiceImpl implements TradeLogService {
	@Resource TradeLogDao tradeLogDao;
	
	@Override
	public ServiceResult<List<TradeLog>> getTradeLogMobile(int tradeId) {
		ServiceResult<List<TradeLog>> returnValue=new ServiceResult<>();
		if(tradeId>0) {
			List<TradeLog> tradeLogList=tradeLogDao.findByTradeId(tradeId);
			if(tradeLogList!=null) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(tradeLogList);
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public List<TradeLog> getTradeLog(int tradeId) throws ServiceException {
		if(tradeId>0) {
			List<TradeLog> tradeLogList=tradeLogDao.findByTradeId(tradeId);
			if(tradeLogList!=null) {
				return tradeLogList;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

}
