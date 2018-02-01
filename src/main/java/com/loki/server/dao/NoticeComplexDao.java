package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.NoticeComplex;

public interface NoticeComplexDao {
	NoticeComplex findByIdMobile(int id);
	List<NoticeComplex> findByParamMobile(Map<String, Object> map);
	List<NoticeComplex> findByParam(Map<String, Object> map);
	NoticeComplex findById(int id);
}
