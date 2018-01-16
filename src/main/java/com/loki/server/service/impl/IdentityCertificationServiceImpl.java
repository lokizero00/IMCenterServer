package com.loki.server.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.UserDao;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.User;
import com.loki.server.service.IdentityCertificationService;

@Service
@Transactional
public class IdentityCertificationServiceImpl implements IdentityCertificationService{
	@Resource IdentityCertificationDao identityCertificationDao;
	@Resource UserDao userDao;
	
	//不存在则创建，存在则更新
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public HashMap<String,Object> updateIdentityCertification(int userId,String trueName,String identityNumber,String identityFront,String identityBack) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if(userId>0) {
			IdentityCertification identityCertification=identityCertificationDao.findByUserId(userId);
			if (null!=identityCertification) {
				if(identityCertification.getStatus().equals("ic_refuse")) {
					identityCertification.setUpdaterId(userId);
					identityCertification.setUserId(userId);
					identityCertification.setTrueName(trueName);
					identityCertification.setIdentityNumber(identityNumber);
					identityCertification.setIdentityFront(identityFront);
					identityCertification.setIdentityBack(identityBack);
					identityCertification.setVerifyTime(null);
					identityCertification.setAdminVerifierId(0);
					identityCertification.setStatus("ic_verify");
					identityCertification.setRefuseReason("");
					if(identityCertificationDao.update(identityCertification)) {
						returnValue.put("resultCode",1);
						returnValue.put("msg","更新成功");
						returnValue.put("resultObj",identityCertification);
					}else {
						returnValue.put("resultCode",7);
						returnValue.put("msg","更新失败");
					}
				}else {
					returnValue.put("resultCode",8);
					returnValue.put("msg","禁止修改");
				}
			}else {
				identityCertification=new IdentityCertification();
				identityCertification.setUserId(userId);
				identityCertification.setTrueName(trueName);
				identityCertification.setIdentityNumber(identityNumber);
				identityCertification.setIdentityFront(identityFront);
				identityCertification.setIdentityBack(identityBack);
				identityCertification.setStatus("ic_verify");
				identityCertification.setCreatorId(userId);
				identityCertificationDao.insert(identityCertification);
				if(identityCertification.getId()>0) {
					//更新user表的实名认证id
					User user=userDao.findById(userId);
					user.setIdentityId(identityCertification.getId());
					userDao.update(user);
					returnValue.put("resultCode",1);
					returnValue.put("msg","保存成功");
					returnValue.put("resultObj",identityCertification);
				}else {
					returnValue.put("resultCode",6);
					returnValue.put("msg","保存失败");
				}
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String, Object> getIdentityCertification(int userId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.put("resultCode",2);
				returnValue.put("msg","用户不存在");
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				returnValue.put("resultCode",1);
				returnValue.put("resultObj",identityCertification);
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}
	
	@Override
	public HashMap<String, Object> getIdentityCertificationStatus(int userId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(user==null) {
				returnValue.put("resultCode",2);
				returnValue.put("msg","用户不存在");
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				if(null==identityCertification) {
					returnValue.put("resultCode",15);
					returnValue.put("msg","实名认证不存在");
				}else {
					returnValue.put("resultCode",1);
					returnValue.put("resultObj",identityCertification.getStatus());
				}
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

}
