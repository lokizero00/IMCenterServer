package com.loki.server.service;

import java.util.List;

import com.loki.server.entity.TopLineNews;
import com.loki.server.vo.ServiceResult;

public interface TopLineNewsService {
	ServiceResult<List<TopLineNews>> getLastest5List();
}
