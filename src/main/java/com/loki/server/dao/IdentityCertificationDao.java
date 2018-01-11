package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.IdentityCertification;

public interface IdentityCertificationDao {
	void insert(IdentityCertification identityCertification);
	boolean update(IdentityCertification identityCertification);
	boolean delete(int id);
	List<IdentityCertification> findAll();
	IdentityCertification findById(int id);
	IdentityCertification findByUserId(int userId);
}
