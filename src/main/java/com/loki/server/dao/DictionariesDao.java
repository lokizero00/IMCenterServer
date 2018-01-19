package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.Dictionaries;

public interface DictionariesDao {
	void insert(Dictionaries tradeResource);
	boolean update(Dictionaries tradeResource);
	boolean delete(int id);
	List<Dictionaries> findAll();
	Dictionaries findById(int id);
	String findValueByParam(@Param("type") String type,@Param("code") String code);
	List<Dictionaries> findListByParam(Map<String,Object> map);
}
