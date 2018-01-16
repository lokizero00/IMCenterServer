package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.EnterpriseCertification;

public interface EnterpriseCertificationDao {
	void insert(EnterpriseCertification enterpriseCertification);
	boolean update(EnterpriseCertification enterpriseCertification);
	boolean delete(int id);
	List<EnterpriseCertification> findAll();
	EnterpriseCertification findById(int id);
	EnterpriseCertification findAllByUserId(int userId);
	EnterpriseCertification findCurrentByUserId(int userId);
	boolean nullifyByUserId(int userId);
}
