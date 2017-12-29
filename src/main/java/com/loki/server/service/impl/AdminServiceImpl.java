package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.AdminDao;
import com.loki.server.model.Admin;
import com.loki.server.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	@Resource private AdminDao adminDao;

	@Override
	public void insert(Admin admin) {
		adminDao.insert(admin);
	}

	@Override
	public boolean update(Admin admin) {
		return adminDao.update(admin);
	}

	@Override
	public boolean delete(int id) {
		return adminDao.delete(id);
	}

	@Override
	public Admin findById(int id) {
		return adminDao.findById(id);
	}

	@Override
	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	@Override
	public Admin loginCheck(String userName, String password) {
		return (Admin) adminDao.loginCheck(userName, password);
	}

}
