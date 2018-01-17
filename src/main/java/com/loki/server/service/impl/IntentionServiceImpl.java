package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.IntentionLogDao;
import com.loki.server.dao.UserBankcardDao;
import com.loki.server.dto.PagedResult;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.UserBankcard;
import com.loki.server.service.IntentionService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;

@Service
@Transactional
public class IntentionServiceImpl implements IntentionService {
	@Resource IntentionDao intentionDao;
	@Resource IntentionLogDao intentionLogDao;
	@Resource UserBankcardDao userBankcardDao;
	
	@Override
	public ServiceResult<Intention> getIntention(int userId) {
		ServiceResult<Intention> returnValue=new ServiceResult<Intention>();
		if(userId>0) {
			Intention intention=intentionDao.findByUserId(userId);
			if(null!=intention) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(intention);
			}else {
				returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<IntentionLog>> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null? 1:pageNo;  
	    pageSize = pageSize == null? 10:pageSize; 
	    ServiceResult<PagedResult<IntentionLog>> returnValue=new ServiceResult<PagedResult<IntentionLog>>();
		
		if(userId>0) {
			PageHelper.startPage(pageNo,pageSize);
			PagedResult<IntentionLog> pageResult=BeanUtil.toPagedResult(intentionLogDao.findByParam(userId, intentionId, adminId, type));
			if(null!=pageResult) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(pageResult);
			}else {
				returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<List<UserBankcard>> getUserBankcard(int userId) {
		ServiceResult<List<UserBankcard>> returnValue=new ServiceResult<List<UserBankcard>>();
		if(userId>0) {
			List<UserBankcard> userBankcardList=userBankcardDao.findByParam(userId, null, null, null, null);
			if(null!=userBankcardList) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(userBankcardList);
			}else {
				returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<UserBankcard> addUserBankcard(UserBankcard userBankcard) {
		ServiceResult<UserBankcard> returnValue=new ServiceResult<UserBankcard>();
		if(userBankcard!=null && userBankcard.getUserId()>0 && userBankcard.getBankCardNumber()!=null && userBankcard.getBankCardNumber()!="") {
			userBankcard.setId(0);
			userBankcardDao.insert(userBankcard);
			if(userBankcard.getId()>0) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(userBankcard);
			}else{
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		}
		else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<UserBankcard> deleteUserBankcard(int userBankcardId) {
		ServiceResult<UserBankcard> returnValue=new ServiceResult<UserBankcard>();
		if(userBankcardId>0) {
			if(userBankcardDao.delete(userBankcardId)) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			}else {
				returnValue.setResultCode(ResultCodeEnums.DELETE_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

}
