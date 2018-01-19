package com.loki.server.service;

import java.util.List;

import com.loki.server.entity.Dictionaries;
import com.loki.server.vo.ServiceResult;

public interface DictionariesService {
	ServiceResult<List<Dictionaries>> getDictionariesListByType(String type);
}
