package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.loki.server.dao.TopLineNewsDao;
import com.loki.server.entity.TopLineNews;
import com.loki.server.service.TopLineNewsService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Service
public class TopLineNewsServiceImpl extends BaseService implements TopLineNewsService {
	@Resource
	TopLineNewsDao topLineNewsDao;

	@Override
	public ServiceResult<List<TopLineNews>> getLastest5List() {
		ServiceResult<List<TopLineNews>> returnValue = new ServiceResult<>();
		try {
			List<TopLineNews> topLineNewsList = topLineNewsDao.findLastest5List();
			if (topLineNewsList != null) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(topLineNewsList);
			} else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} catch (Exception e) {
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

}
