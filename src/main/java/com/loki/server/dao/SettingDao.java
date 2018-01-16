package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.Setting;

public interface SettingDao {
	void insert(Setting setting);
	boolean update(Setting setting);
	Setting findById(int id);
	List<Setting> findAll();
	boolean delete(int id);
}
