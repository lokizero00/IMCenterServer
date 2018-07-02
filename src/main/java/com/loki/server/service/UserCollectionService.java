package com.loki.server.service;

import com.loki.server.vo.ServiceResult;

public interface UserCollectionService {
	ServiceResult<Void> addCollection(Integer userId,Integer tradeId,String type);
	ServiceResult<Void> delCollection(Integer userCollectionId);
}
