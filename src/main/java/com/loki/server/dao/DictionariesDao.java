package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.Dictionaries;

public interface DictionariesDao {
	void insert(Dictionaries tradeResource);
	boolean update(Dictionaries tradeResource);
	boolean delete(int id);
	List<Dictionaries> findAll();
	Dictionaries findById(int id);
	String findValueByCode(String code);
}
