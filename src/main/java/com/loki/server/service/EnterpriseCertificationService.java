package com.loki.server.service;

import java.util.List;

import com.loki.server.model.EnterpriseCertification;

public interface EnterpriseCertificationService {
	void insert(EnterpriseCertification enterpriseCertification);
	boolean update(EnterpriseCertification enterpriseCertification);
	boolean delete(int id);
	List<EnterpriseCertification> findAll();
	EnterpriseCertification findById(int id);
	EnterpriseCertification findByUserId(int id);
	boolean nullifyByUserId(int userId);
}
