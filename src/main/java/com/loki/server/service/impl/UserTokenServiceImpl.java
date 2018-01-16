package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.UserTokenDao;
import com.loki.server.entity.UserToken;
import com.loki.server.service.UserTokenService;

@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService{
	@Resource UserTokenDao userTokenDao;

	@Override
	public void insert(UserToken userToken) {
		userTokenDao.insert(userToken);
	}

	@Override
	public boolean delete(int id) {
		return userTokenDao.delete(id);
	}

	@Override
	public boolean expireByUserId(int userId) {
		return userTokenDao.expireByUserId(userId);
	}

	@Override
	public List<UserToken> findAll() {
		return userTokenDao.findAll();
	}

	@Override
	public List<UserToken> findByUserId(int userId) {
		return userTokenDao.findByUserId(userId);
	}

	@Override
	public int tokenCheck(String token) {
		return userTokenDao.tokenCheck(token);
	}

}
