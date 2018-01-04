package com.loki.server.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.UserDao;
import com.loki.server.model.EnterpriseCertification;
import com.loki.server.model.IdentityCertification;
import com.loki.server.model.Intention;
import com.loki.server.model.User;
import com.loki.server.service.PersonalCenterService;

@Service
@Transactional
public class PersonalCenterServiceImpl implements PersonalCenterService {
	@Resource private UserDao userDao;
	@Resource private IdentityCertificationDao identityCertificationDao;
	@Resource private EnterpriseCertificationDao enterpriseCertificationDao;
	@Resource private IntentionDao intentionDao;

	@Override
	public HashMap<String,Object> getPersonalCenter(int userId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(user==null) {
				returnValue.put("resultCode", 2);
				returnValue.put("msg","用户不存在");
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findById(user.getEnterpriseId());
				Intention intention=intentionDao.findByUserId(user.getId());
				HashMap<String,Object> pcMap=new HashMap<String,Object>();
				pcMap.put("user", user);
				pcMap.put("identityCertification",identityCertification);
				pcMap.put("enterpriseCertification",enterpriseCertification);
				pcMap.put("intention",intention);
				
				returnValue.put("resultCode",1);
				returnValue.put("resultObj",pcMap);
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String,Object> getUser(int userId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(user==null) {
				returnValue.put("resultCode",2);
				returnValue.put("msg","用户不存在");
			}else {
				returnValue.put("resultCode",1);
				returnValue.put("resultObj",user);
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	//实名认证
	//不存在则创建，存在则更新
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public HashMap<String,Object> updateIdentityCertification(IdentityCertification identityCertification) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if(identityCertification!=null && identityCertification.getUserId()>0) {
			int existCount=identityCertificationDao.existByUserId(identityCertification.getUserId());
			if (existCount>0) {
				if(identityCertification.getStatus().equals("ic_refuse")) {
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
				identityCertificationDao.insert(identityCertification);
				if(identityCertification.getId()>0) {
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

	//企业认证
	//不存在则创建，存在且为已拒绝则更新，存在且为已认证则删除之前认证重新创建
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public HashMap<String,Object> updateEnterpriseCertification(EnterpriseCertification enterpriseCertification) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if(enterpriseCertification!=null && enterpriseCertification.getUserId()>0) {
			int existCount=enterpriseCertificationDao.existByUserId(enterpriseCertification.getUserId());
			if(existCount>0) {
				//存在deleted为0的企业认证时，判断此认证的状态，待审核则不让修改，已认证则删除后重新添加，已拒绝则修改
				EnterpriseCertification current=enterpriseCertificationDao.findCurrentByUserId(enterpriseCertification.getUserId());
				if(current.getStatus().equals("ec_verify")) {
					returnValue.put("resultCode",8);
					returnValue.put("msg","禁止修改");
				}else if (current.getStatus().equals("ec_pass")) {
					enterpriseCertificationDao.nullifyByUserId(enterpriseCertification.getUserId());
					enterpriseCertification.setId(0);
					enterpriseCertificationDao.insert(enterpriseCertification);
					if(enterpriseCertification.getId()>0) {
						returnValue.put("resultCode",1);
						returnValue.put("msg","保存成功");
						returnValue.put("resultObj",enterpriseCertification);
					}else {
						returnValue.put("resultCode",6);
						returnValue.put("msg","保存失败");
					}
				}else if(current.getStatus().equals("ec_refuse")) {
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
				enterpriseCertificationDao.insert(enterpriseCertification);
				if(enterpriseCertification.getId()>0) {
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

}
