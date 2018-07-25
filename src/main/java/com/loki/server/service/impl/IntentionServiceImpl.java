package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.DictionariesDao;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.IntentionLogDao;
import com.loki.server.dao.IntentionRefundDao;
import com.loki.server.dao.UserBankcardDao;
import com.loki.server.dto.IntentionDTO;
import com.loki.server.dto.IntentionLogDTO;
import com.loki.server.dto.IntentionRefundRequestDTO;
import com.loki.server.dto.convertor.IntentionConvertor;
import com.loki.server.dto.convertor.IntentionLogConvertor;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.IntentionRefund;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserBankcard;
import com.loki.server.service.IntentionService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class IntentionServiceImpl extends BaseService implements IntentionService {
	@Resource IntentionDao intentionDao;
	@Resource IntentionLogDao intentionLogDao;
	@Resource UserBankcardDao userBankcardDao;
	@Resource DictionariesDao dictionariesDao;
	@Resource IntentionRefundDao intentionRefundDao;
	
	DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public ServiceResult<Intention> getIntention_mobile(int userId) {
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
	public ServiceResult<PagedResult<IntentionLog>> getIntentionLog_mobile(Map<String,Object> map,Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null? 1:pageNo;  
	    pageSize = pageSize == null? 10:pageSize; 
	    ServiceResult<PagedResult<IntentionLog>> returnValue=new ServiceResult<PagedResult<IntentionLog>>();
		
		if(map!=null) {
			PageHelper.startPage(pageNo,pageSize);
			PagedResult<IntentionLog> pageResult=BeanUtil.toPagedResult(intentionLogDao.findByParam(map));
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
	public ServiceResult<List<UserBankcard>> getUserBankcard_mobile(int userId) {
		ServiceResult<List<UserBankcard>> returnValue=new ServiceResult<List<UserBankcard>>();
		if(userId>0) {
			List<UserBankcard> userBankcardList=userBankcardDao.findByParam(userId, null, null, null);
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
	public ServiceResult<Void> addUserBankcard_mobile(UserBankcard userBankcard) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
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
	public ServiceResult<Void> deleteUserBankcard_mobile(int userBankcardId) {
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

	@Override
	public PagedResult<IntentionDTO> getIntentionList(Map<String, Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<Intention> intentionList=intentionDao.findByParam(map);
			List<IntentionDTO> intentionDTOList=new ArrayList<>();
			for(Intention intention:intentionList) {
				IntentionDTO intentionDTO=IntentionConvertor.convertIntention2IntentionDTO(intention);
				intentionDTO.setUserNickName(getUserNickName(intentionDTO.getUserId()));
				intentionDTO.setPhone(getUserPhoneByUserId(intentionDTO.getUserId()));
				intentionDTO.setRealName(getIdentityNameByUserId(intentionDTO.getUserId()));
				intentionDTOList.add(intentionDTO);
			}
			PagedResult<IntentionDTO> pageResult=BeanUtil.toPagedResult(intentionDTOList);
			if(pageResult!=null) {
				return pageResult;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public IntentionDTO getUserIntention(int userId) throws ServiceException {
		if(userId>0) {
			Intention intention=intentionDao.findByUserId(userId);
			if(intention!=null) {
				IntentionDTO intentionDTO=IntentionConvertor.convertIntention2IntentionDTO(intention);
				if(intentionDTO!=null) {
					intentionDTO.setUserNickName(getUserNickName(intentionDTO.getUserId()));
					intentionDTO.setPhone(getUserPhoneByUserId(intentionDTO.getUserId()));
					intentionDTO.setRealName(getIdentityNameByUserId(intentionDTO.getUserId()));
					return intentionDTO;
				}else {
					throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public IntentionDTO getIntention(int intentionId) throws ServiceException {
		if(intentionId>0) {
			Intention intention=intentionDao.findById(intentionId);
			if(intention!=null) {
				IntentionDTO intentionDTO=IntentionConvertor.convertIntention2IntentionDTO(intention);
				if(intentionDTO!=null) {
					intentionDTO.setUserNickName(getUserNickName(intentionDTO.getUserId()));
					intentionDTO.setPhone(getUserPhoneByUserId(intentionDTO.getUserId()));
					intentionDTO.setRealName(getIdentityNameByUserId(intentionDTO.getUserId()));
					return intentionDTO;
				}else {
					throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<IntentionLogDTO> getIntentionLog(Map<String, Object> map) {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<IntentionLog> intentionLogList=intentionLogDao.findByParam(map);
			List<IntentionLogDTO> intentionLogDTOList=new ArrayList<>();
			for(IntentionLog intentionLog:intentionLogList) {
				IntentionLogDTO intentionLogDTO=IntentionLogConvertor.convertIntentionLog2IntentionLogDTO(intentionLog);
				intentionLogDTO.setLogRoleName(getDictionariesValue("operator_role", intentionLogDTO.getLogRole()));
				intentionLogDTO.setRelationTypeName(getDictionariesValue("intention_change_type", intentionLogDTO.getRelationType()));
				String logOperatorName=intentionLogDTO.getLogRole().equals("user") ? getUserName(intentionLogDTO.getLogOperatorId()) : getAdminName(intentionLogDTO.getLogOperatorId());
				intentionLogDTO.setLogOperatorName(logOperatorName);
				intentionLogDTOList.add(intentionLogDTO);
			}
			PagedResult<IntentionLogDTO> pageResult=BeanUtil.toPagedResult(intentionLogDTOList);
			if(pageResult!=null) {
				return pageResult;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}
	
	/**
     * 提现申请
     * @param intentionRefundRequestDTO
     * @return
     */
	@Override
    @Transactional
    //TODO 提现时，需要先把钱转到提现中的字段里，等待成功或失败后，更新账户信息
    public ServiceResult<Void> returnIntention(IntentionRefundRequestDTO intentionRefundRequestDTO){
    		ServiceResult<Void> returnValue=new ServiceResult<>();
    		if(intentionRefundRequestDTO!=null && intentionRefundRequestDTO.getAmount().compareTo(BigDecimal.ZERO)==1) {
    			//拿到账户
    	        Intention intention=intentionDao.findByUserId(intentionRefundRequestDTO.getUserId());
    	        if(intention!=null) {
    	        		if (!(intention.getAvailable().compareTo(intentionRefundRequestDTO.getAmount())==-1)) {
    	        			IntentionRefund intentionRefund=new IntentionRefund();
    	        			intentionRefund.setIntentionId(intention.getId());
    	        			intentionRefund.setUserId(intention.getUserId());
    	        			intentionRefund.setRequestTime(new Timestamp(System.currentTimeMillis()));
    	        			intentionRefund.setAmount(intentionRefundRequestDTO.getAmount());
    	        			intentionRefund.setRefundItem(0);
    	        			intentionRefund.setState(0);
    	        			intentionRefund.setRefundType(1);
    	        			intentionRefund.setRefundChannel(intentionRefundRequestDTO.getRefundChannel());
    	        			intentionRefund.setRefundAccount(intentionRefundRequestDTO.getRefundAccount());
    	        			intentionRefundDao.insert(intentionRefund);
    	        			returnValue.setResultCode(ResultCodeEnums.SUCCESS);
    	        		}else {
    	        			returnValue.setResultCode(ResultCodeEnums.INTENTION_AVAILABLE_NOT_ENOUGH);
    	        		}
    	        }else {
    	        		returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
    	        }
    		}else {
    			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
    		}
        return returnValue;
    }

}
