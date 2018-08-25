package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.loki.server.dao.TradeDao;
import com.loki.server.dao.UserCollectionDao;
import com.loki.server.entity.UserCollection;
import com.loki.server.service.UserCollectionService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Service
public class UserCollectionServiceImpl extends BaseService implements UserCollectionService {
	@Resource
	UserCollectionDao userCollectionDao;
	@Resource
	TradeDao tradeDao;

	@Override
	public ServiceResult<Void> addCollection(Integer userId, Integer tradeId, String type) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		if (userId != null && tradeId != null && userId > 0 && tradeId > 0) {
			//校验是否已收藏
			int collectionCount=userCollectionDao.checkExist(tradeId, userId);
			if(collectionCount<=0) {
				UserCollection uc = new UserCollection();
				uc.setCreatorId(userId);
				uc.setTradeId(tradeId);
				uc.setUserId(userId);
				uc.setType(type);
				userCollectionDao.insert(uc);
				
				if (uc.getId() > 0) {
					tradeDao.updateCollectionCountAdd(tradeId);
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				} else {
					returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> delCollection(Integer userCollectionId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		if (userCollectionId != null && userCollectionId > 0) {
			UserCollection userCollection=userCollectionDao.findById(userCollectionId);
			if(userCollection!=null) {
				boolean result = userCollectionDao.delete(userCollection.getId());
				if (result) {
					tradeDao.updateCollectionCountSub(userCollection.getTradeId());
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DELETE_FAIL);
				}
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
