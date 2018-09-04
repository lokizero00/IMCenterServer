package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.IntentionJournalDao;
import com.loki.server.dto.IntentionRechargeDTO;
import com.loki.server.dto.RechargeDTO;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionJournal;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.PayService;
import com.loki.server.service.WeiXinAndAliService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.BillConst;
import com.loki.server.utils.OrderNoGenerator;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

@Service
public class PayServiceImpl extends BaseService implements PayService {
	@Resource IntentionJournalDao intentionJournalDao;
	@Resource WeiXinAndAliService weiXinAndAliService;
	@Resource IntentionDao intentionDao;

	@Override
	public ServiceResult<RechargeDTO> intentionRecharge(IntentionRechargeDTO intentionRechargeDTO,String ip) {
		ServiceResult<RechargeDTO> result=new ServiceResult<>();
		try {
			if(intentionRechargeDTO.getUserId()!=null && intentionRechargeDTO.getUserId()>0 && intentionRechargeDTO.getPayType()!=null && intentionRechargeDTO.getPayType()>0 && intentionRechargeDTO.getRechargeAmount()!=null) {
				if(intentionRechargeDTO.getRechargeAmount().compareTo(BigDecimal.ZERO)==1) {
					RechargeDTO rechargeDTO = new RechargeDTO();
			        rechargeDTO.setPayType(intentionRechargeDTO.getPayType());
			        Intention intention=intentionDao.findByUserId(intentionRechargeDTO.getUserId());
			        if(intention!=null) {
			        		// 记录充值信息
				        IntentionJournal intentionJournal = new IntentionJournal();
				        intentionJournal.setType(BillConst.JournalType.RECHARGE.getKey());
				        intentionJournal.setIntentionId(intention.getId());
				        intentionJournal.setUserId(intentionRechargeDTO.getUserId());
				        intentionJournal.setInnerBusiNo(OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.RECHARGE.getKey()));
				        intentionJournal.setAmount(intentionRechargeDTO.getRechargeAmount());
				        intentionJournal.setState(BillConst.JournalState.TOBECONFIRMED.getKey());
				        intentionJournal.setCheckState(BillConst.JournalCheckState.NOTRECONCILED.getKey());
				        intentionJournal.setThirdChannel(intentionRechargeDTO.getPayType().toString());
				        intentionJournal.setNeedThirdConfirm(1);
				        intentionJournalDao.insert(intentionJournal);
				        
				        if(intentionJournal.getId()>0) {
				        		result = weiXinAndAliService.unifiedOrder(intentionJournal.getInnerBusiNo(),intentionJournal.getAmount(),ip,intentionRechargeDTO.getPayType());
				        }else {
				        	result.setResultCode(ResultCodeEnums.SAVE_FAIL);
				        }
			        }else {
			        		result.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
			        }
				}else {
					result.setResultCode(ResultCodeEnums.RECHARGE_AMOUNT_ZERO);
				}
			}else {
				result.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		}catch(Exception e) {
			e.printStackTrace();
			result.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
        return result;
	}

	@Override
	public ServiceResult<PagedResult<IntentionJournal>> getIntentionJournal_mobile(Map<String, Object> map) {
		ServiceResult<PagedResult<IntentionJournal>> returnValue = new ServiceResult<>();
		try {
			if (map != null) {
				int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
				int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
				PageHelper.startPage(pageNo, pageSize);
				PagedResult<IntentionJournal> intentionJournalList = BeanUtil.toPagedResult(intentionJournalDao.findByParam(map));
				if (intentionJournalList != null) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(intentionJournalList);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public PagedResult<IntentionJournal> getIntentionJournal(Map<String, Object> map) {
		try {
			if (map != null) {
				int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
				int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
				PageHelper.startPage(pageNo, pageSize);
				PagedResult<IntentionJournal> intentionJournalList = BeanUtil.toPagedResult(intentionJournalDao.findByParam(map));
				if (intentionJournalList != null) {
					for(IntentionJournal intentionJournal : intentionJournalList.getRows()) {
						intentionJournal.setUserName(getUserName(intentionJournal.getUserId()));;
					}
					return intentionJournalList;
				} else {
					throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ResultCodeEnums.UNKNOW_ERROR);
		}
	}

}
