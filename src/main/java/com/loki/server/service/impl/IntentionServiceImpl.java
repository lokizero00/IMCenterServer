package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.DictionariesDao;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.IntentionLogDao;
import com.loki.server.dao.UserBankcardDao;
import com.loki.server.entity.Intention;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserBankcard;
import com.loki.server.service.IntentionService;
import com.loki.server.utils.BeanMapper;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.IntentionLogVO;
import com.loki.server.vo.IntentionVO;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserBankcardVO;

@Service
@Transactional
public class IntentionServiceImpl implements IntentionService {
	@Resource IntentionDao intentionDao;
	@Resource IntentionLogDao intentionLogDao;
	@Resource UserBankcardDao userBankcardDao;
	@Resource DictionariesDao dictionariesDao;
	
	DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public ServiceResult<IntentionVO> getIntention(int userId) {
		ServiceResult<IntentionVO> returnValue=new ServiceResult<IntentionVO>();
		if(userId>0) {
			Intention intention=intentionDao.findByUserId(userId);
			if(null!=intention) {
				IntentionVO intentionVO=BeanMapper.map(intention, IntentionVO.class);
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(intentionVO);
			}else {
				returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<IntentionLogVO>> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null? 1:pageNo;  
	    pageSize = pageSize == null? 10:pageSize; 
	    ServiceResult<PagedResult<IntentionLogVO>> returnValue=new ServiceResult<PagedResult<IntentionLogVO>>();
		
		if(userId>0) {
			PageHelper.startPage(pageNo,pageSize);
			PagedResult<IntentionLogVO> pageResult=BeanUtil.toPagedResult(BeanMapper.mapList(intentionLogDao.findByParam(userId, intentionId, adminId, type), IntentionLogVO.class));
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
	public ServiceResult<List<UserBankcardVO>> getUserBankcard(int userId) {
		ServiceResult<List<UserBankcardVO>> returnValue=new ServiceResult<List<UserBankcardVO>>();
		if(userId>0) {
			List<UserBankcard> userBankcardList=userBankcardDao.findByParam(userId, null, null, null);
			if(null!=userBankcardList) {
				List<UserBankcardVO> userBankcardVOList= BeanMapper.mapList(userBankcardList, UserBankcardVO.class);
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(userBankcardVOList);
			}else {
				returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> addUserBankcard(UserBankcardVO userBankcardVO) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		UserBankcard userBankcard=BeanMapper.map(userBankcardVO, UserBankcard.class);
		if(userBankcard!=null && userBankcard.getUserId()>0 && userBankcard.getCardNumber()!=null && userBankcard.getCardNumber()!="" && userBankcard.getBankCode()!=null && userBankcard.getBankCode()!="" && userBankcard.getCardTypeCode()!=null && userBankcard.getCardTypeCode()!="") {
			String bankName=dictionariesDao.findValueByParam("bank", userBankcard.getBankCode());
			if(bankName!=null && bankName!="") {
				userBankcard.setId(0);
				userBankcard.setBankName(bankName);
				String cardTypeName=dictionariesDao.findValueByParam("bank_card_type", userBankcard.getCardTypeCode());
				userBankcard.setCardTypeName(cardTypeName);
				userBankcardDao.insert(userBankcard);
				if(userBankcard.getId()>0) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				}else{
					returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.BANK_CODE_ERROR);
			}
		}
		else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> deleteUserBankcard(int userBankcardId) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
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
