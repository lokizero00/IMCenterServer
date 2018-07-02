package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.IdentityCertification;

public interface IdentityCertificationDao {
	void insert(IdentityCertification identityCertification);
	boolean update(IdentityCertification identityCertification);
	boolean delete(int id);
	List<IdentityCertification> findAll();
	IdentityCertification findById(int id);
	IdentityCertification findByUserId(int userId);
	IdentityCertification findByIdAndUserId(@Param("id") int id,@Param("userId") int userId);
	List<IdentityCertification> findByParam(Map<String,Object> map);
	String findStatusById(int id);
	String findIdentityNameById(int id);
}
