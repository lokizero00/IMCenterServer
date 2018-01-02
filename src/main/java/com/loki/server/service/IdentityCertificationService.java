package com.loki.server.service;

import java.util.List;

import com.loki.server.model.IdentityCertification;

public interface IdentityCertificationService {
	void insert(IdentityCertification identityCertification);
	boolean update(IdentityCertification identityCertification);
	boolean delete(int id);
	List<IdentityCertification> findAll();
	IdentityCertification findById(int id);
}
