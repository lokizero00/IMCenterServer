package com.loki.server.service;

import java.util.List;

import com.loki.server.entity.Dictionaries;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

public interface DictionariesService {
	ServiceResult<List<Dictionaries>> getDictionariesListMobile(String type);
	List<Dictionaries> getDictionariesList(String type)  throws ServiceException;
}
