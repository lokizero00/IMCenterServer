package com.loki.server.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.UserDao;
import com.loki.server.model.EnterpriseCertification;
import com.loki.server.model.User;
import com.loki.server.service.EnterpriseCertificationService;

@Service
@Transactional
public class EnterpriseCertificationServiceImpl implements EnterpriseCertificationService{
	@Resource EnterpriseCertificationDao enterpriseCertificationDao;
	@Resource UserDao userDao;
	
	//企业认证
	//不存在则创建，存在且为已拒绝则更新，存在且为已认证则删除之前认证重新创建
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public HashMap<String,Object> updateEnterpriseCertification(int userId,String position,String enterpriseName,String licensePic) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if(userId>0) {
			EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findCurrentByUserId(userId);
			if(null!=enterpriseCertification) {
				//存在deleted为0的企业认证时，判断此认证的状态，待审核则不让修改，已认证则删除后重新添加，已拒绝则修改
				if(enterpriseCertification.getStatus().equals("ec_verify")) {
					returnValue.put("resultCode",8);
					returnValue.put("msg","禁止修改");
				}else if (enterpriseCertification.getStatus().equals("ec_pass")) {
					enterpriseCertificationDao.nullifyByUserId(userId);
					enterpriseCertification=new EnterpriseCertification();
					enterpriseCertification.setCreatorId(userId);
					enterpriseCertification.setPosition(position);
					enterpriseCertification.setEnterpriseName(enterpriseName);
					enterpriseCertification.setLicensePic(licensePic);
					enterpriseCertification.setUserId(userId);
					enterpriseCertificationDao.insert(enterpriseCertification);
					if(enterpriseCertification.getId()>0) {
						//更新user表的企业认证id
						User user=userDao.findById(userId);
						user.setEnterpriseId(enterpriseCertification.getId());
						userDao.update(user);
						
						returnValue.put("resultCode",1);
						returnValue.put("msg","保存成功");
						returnValue.put("resultObj",enterpriseCertification);
					}else {
						returnValue.put("resultCode",6);
						returnValue.put("msg","保存失败");
					}
				}else if(enterpriseCertification.getStatus().equals("ec_refuse")) {
					enterpriseCertification.setUpdaterId(userId);
					enterpriseCertification.setUserId(userId);
					enterpriseCertification.setPosition(position);
					enterpriseCertification.setEnterpriseName(enterpriseName);
					enterpriseCertification.setLicensePic(licensePic);
					enterpriseCertification.setVerifyTime(null);
					enterpriseCertification.setAdminVerifierId(0);
					enterpriseCertification.setStatus("ec_verify");
					enterpriseCertification.setRefuseReason("");
					if(enterpriseCertificationDao.update(enterpriseCertification)) {
						returnValue.put("resultCode",1);
						returnValue.put("msg","更新成功");
						returnValue.put("resultObj",enterpriseCertification);
					}else {
						returnValue.put("resultCode",7);
						returnValue.put("msg","更新失败");
					}
				}else {
					returnValue.put("resultCode",-1);
					returnValue.put("msg","未知错误");
				}
			}else {
				enterpriseCertification=new EnterpriseCertification();
				enterpriseCertification.setCreatorId(userId);
				enterpriseCertification.setPosition(position);
				enterpriseCertification.setEnterpriseName(enterpriseName);
				enterpriseCertification.setLicensePic(licensePic);
				enterpriseCertification.setUserId(userId);
				enterpriseCertificationDao.insert(enterpriseCertification);
				if(enterpriseCertification.getId()>0) {
					//更新user表的企业认证id
					User user=userDao.findById(userId);
					user.setEnterpriseId(enterpriseCertification.getId());
					userDao.update(user);
					
					returnValue.put("resultCode",1);
					returnValue.put("msg","保存成功");
					returnValue.put("resultObj",enterpriseCertification);
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
	public HashMap<String, Object> getEnterpriseCertification(int userId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.put("resultCode",2);
				returnValue.put("msg","用户不存在");
			}else {
				EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findById(user.getEnterpriseId());
				returnValue.put("resultCode",1);
				returnValue.put("resultObj",enterpriseCertification);
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}
}
