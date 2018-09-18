package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.AppVersion;

public interface AppVersionDao {
	void insert(AppVersion appVersion);
	boolean update(AppVersion appVersion);
	boolean delete(int id);
	List<AppVersion> findAll();
	AppVersion findById(int id);
	AppVersion findByAppId(String appId);
}
