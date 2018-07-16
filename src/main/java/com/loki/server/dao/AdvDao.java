package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.Adv;

public interface AdvDao {
	void insert(Adv adv);
	boolean update(Adv adv);
	boolean delete(int id);
	List<Adv> findAll();
	Adv findById(int id);
	List<Adv> findByParam(Map<String,Object> map);
	List<Adv> findByCount(int count);
}
