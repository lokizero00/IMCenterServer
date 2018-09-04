package com.loki.server.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.loki.server.dao.IntentionRefundDao;
import com.loki.server.dto.IntentionRefundDTO;
import com.loki.server.dto.RoleDTO;
import com.loki.server.dto.convertor.RoleConvertor;
import com.loki.server.entity.IntentionRefund;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Role;
import com.loki.server.service.IntentionRefundService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;

@Service
@Transactional
public class IntentionRefundServiceImpl  extends BaseService implements IntentionRefundService{

	@Resource IntentionRefundDao intentionRefundDao;

	@Override
	public boolean addIntentionRefund(IntentionRefundDTO intentionRefundDto) throws ServiceException {
		// TODO Auto-generated method stub
		if(intentionRefundDto!=null) {
			IntentionRefund intentionRefund=new IntentionRefund();
			intentionRefund.setUserId(intentionRefundDto.getUserId());
			intentionRefund.setIntentionId(intentionRefundDto.getIntentionId());
			intentionRefund.setRequestTime(new Timestamp(System.currentTimeMillis()));
			intentionRefund.setAmount(intentionRefundDto.getAmount());
			intentionRefund.setRefundItem(intentionRefundDto.getRefundItem());
			intentionRefund.setJournalId(intentionRefundDto.getJournalId());
			intentionRefund.setState(intentionRefundDto.getState());
			intentionRefund.setOutRequestNo(intentionRefundDto.getOutRequestNo());
			intentionRefund.setRefundChannel(intentionRefundDto.getRefundChannel());
			intentionRefund.setRefundType(intentionRefundDto.getRefundType());
			intentionRefundDao.insert(intentionRefund);
			if (intentionRefund.getId()>0) {
				return true;
			}else {
				throw new ServiceException(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean editIntentionRefund(IntentionRefundDTO intentionRefundDto) throws ServiceException {
		// TODO Auto-generated method stub
		if(intentionRefundDto!=null && intentionRefundDto.getId()>0) {
			IntentionRefund intentionRefund=intentionRefundDao.findById(intentionRefundDto.getId());
			if(intentionRefund!=null) {
				intentionRefund.setUserId(intentionRefundDto.getUserId());
				intentionRefund.setIntentionId(intentionRefundDto.getIntentionId());
				intentionRefund.setRequestTime(new Timestamp(System.currentTimeMillis()));
				intentionRefund.setFinishTime(new Timestamp(System.currentTimeMillis()));
				intentionRefund.setAmount(intentionRefundDto.getAmount());
				intentionRefund.setRefundItem(intentionRefundDto.getRefundItem());
				intentionRefund.setJournalId(intentionRefundDto.getJournalId());
				intentionRefund.setState(intentionRefundDto.getState());
				intentionRefund.setOutRequestNo(intentionRefundDto.getOutRequestNo());
				intentionRefund.setRefundChannel(intentionRefundDto.getRefundChannel());
				intentionRefund.setRefundType(intentionRefundDto.getRefundType());
				if(intentionRefundDao.update(intentionRefund)) {
					return true;
				}else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public IntentionRefundDTO getIntentionRefund(int id) throws ServiceException {
		// TODO Auto-generated method stub
		if(id>0) {
			IntentionRefund intentionRefund=intentionRefundDao.findById(id);
			if(intentionRefund!=null) {
				IntentionRefundDTO intentionRefundDto=new IntentionRefundDTO();
				intentionRefundDto.setUserName(getUserName(intentionRefund.getUserId()));
				intentionRefundDto.setPhone(getUserPhoneByUserId(intentionRefund.getUserId()));
				return intentionRefundDto;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<IntentionRefundDTO> getIntentionRefundList(Map<String, Object> map) throws ServiceException {
		// TODO Auto-generated method stub
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<IntentionRefund> intentionRefundList=intentionRefundDao.findByParam(map);
			if(intentionRefundList!=null) {
				List<IntentionRefundDTO> refundDTOList=new ArrayList<>();
				for(IntentionRefund intentionRefund:intentionRefundList) {
					IntentionRefundDTO refundDto=new IntentionRefundDTO();
					refundDto.setUserName(getUserName(intentionRefund.getUserId()));
					refundDto.setPhone(getUserPhoneByUserId(intentionRefund.getUserId()));
					refundDTOList.add(refundDto);
				}
				Page data=(Page) intentionRefundList;
				PagedResult<IntentionRefundDTO> pagedList=BeanUtil.toPagedResult(refundDTOList,data.getPageNum(),data.getPageSize(),data.getTotal(),data.getPages());
				if(pagedList!=null) {
					return pagedList;
				}else {
					throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean delIntentionRefund(int id) throws ServiceException {
		// TODO Auto-generated method stub
		if(id>0) {
			return intentionRefundDao.delete(id);
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}
	

}
