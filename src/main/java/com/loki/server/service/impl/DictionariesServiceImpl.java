package com.loki.server.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.DictionariesDao;
import com.loki.server.entity.Dictionaries;
import com.loki.server.service.DictionariesService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class DictionariesServiceImpl implements DictionariesService {
	@Resource DictionariesDao dictionariesDao;
	
	@Override
	public ServiceResult<List<Dictionaries>> getDictionariesListMobile(String type) {
		ServiceResult<List<Dictionaries>> returnValue=new ServiceResult<List<Dictionaries>>();
		HashMap<String,Object> map=new HashMap<>();
		map.put("type", type);
		List<Dictionaries> dictionariesList=dictionariesDao.findListByParam(map);
		if(dictionariesList!=null) {
			returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			returnValue.setResultObj(dictionariesList);
		}else {
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public List<Dictionaries> getDictionariesList(String type) throws ServiceException{
		HashMap<String,Object> map=new HashMap<>();
		map.put("type", type);
		List<Dictionaries> dictionariesList=dictionariesDao.findListByParam(map);
		if(dictionariesList!=null) {
			return dictionariesList;
		}else {
			throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
		}
	}

}
