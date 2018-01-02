package com.loki.server.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.AdminDao;
import com.loki.server.dao.AdminLogDao;
import com.loki.server.model.Admin;
import com.loki.server.model.AdminLog;
import com.loki.server.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	@Resource private AdminDao adminDao;
	@Resource private AdminLogDao adminLogDao;

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

	//TODO 这里有问题，第一行的loginCheck执行后，在RedisCache中的put方法中，value是null。很奇怪！
	@Override
	public Admin login(String userName, String password,String clientIP) {
		Admin admin=adminDao.loginCheck(userName, password);
		if (admin==null) return null;
		
		admin.setLoginTime(new Timestamp(System.currentTimeMillis()));
		admin.setLoginCount(admin.getLoginCount()+1);
		
		boolean update=adminDao.update(admin);
		if (!update) return null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AdminLog adminLog=new AdminLog();
		adminLog.setAdminId(admin.getId());
		adminLog.setAdminName(admin.getUserName());
		adminLog.setIp(clientIP);
		adminLog.setContent("管理员id:"+admin.getId()+",name:"+admin.getUserName()+",在"+dateFormat.format(new Date())+"登录了系统");
		adminLogDao.insert(adminLog);
		
		return admin;
	}

}
