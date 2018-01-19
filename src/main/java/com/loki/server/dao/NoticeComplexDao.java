package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.NoticeComplex;

public interface NoticeComplexDao {
	NoticeComplex findById(int id);
	List<NoticeComplex> findByParam(Map<String, Object> map);
}
