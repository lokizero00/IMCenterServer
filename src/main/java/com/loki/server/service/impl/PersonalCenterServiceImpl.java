package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.complexModel.PersonalCenterComplex;
import com.loki.server.complexModel.ServiceReturnModel;
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
	public ServiceReturnModel getPersonalCenter(int userId) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(user==null) {
				returnValue.setResultCode(2);
				returnValue.setMsg("用户不存在");
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findById(user.getEnterpriseId());
				Intention intention=intentionDao.findByUserId(user.getId());
				PersonalCenterComplex pcc=new PersonalCenterComplex();
				pcc.setUser(user);
				pcc.setIdentityCertification(identityCertification);
				pcc.setEnterpriseCertification(enterpriseCertification);
				pcc.setIntention(intention);
				
				returnValue.setResultCode(1);
				returnValue.setResultObj(pcc);
			}
		}else {
			returnValue.setResultCode(3);
			returnValue.setMsg("参数错误");
		}
		return returnValue;
	}

	@Override
	public ServiceReturnModel getUser(int userId) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(user==null) {
				returnValue.setResultCode(2);
				returnValue.setMsg("用户不存在");
			}else {
				returnValue.setResultCode(1);
				returnValue.setResultObj(user);
			}
		}else {
			returnValue.setResultCode(3);
			returnValue.setMsg("参数错误");
		}
		return returnValue;
	}

	//实名认证
	//不存在则创建，存在则更新
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public ServiceReturnModel updateIdentityCertification(IdentityCertification identityCertification) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if(identityCertification!=null && identityCertification.getUserId()>0) {
			int existCount=identityCertificationDao.existByUserId(identityCertification.getUserId());
			if (existCount>0) {
				if(identityCertification.getStatus().equals("ic_refuse")) {
					identityCertification.setStatus("ic_verify");
					identityCertification.setRefuseReason("");
					if(identityCertificationDao.update(identityCertification)) {
						returnValue.setResultCode(1);
						returnValue.setMsg("更新成功");
						returnValue.setResultObj(identityCertification);
					}else {
						returnValue.setResultCode(7);
						returnValue.setMsg("更新失败");
					}
				}else {
					returnValue.setResultCode(8);
					returnValue.setMsg("禁止修改");
				}
			}else {
				identityCertificationDao.insert(identityCertification);
				if(identityCertification.getId()>0) {
					returnValue.setResultCode(1);
					returnValue.setMsg("保存成功");
					returnValue.setResultObj(identityCertification);
				}else {
					returnValue.setResultCode(6);
					returnValue.setMsg("保存失败");
				}
			}
		}else {
			returnValue.setResultCode(3);
			returnValue.setMsg("参数错误");
		}
		return returnValue;
	}

	//企业认证
	//不存在则创建，存在且为已拒绝则更新，存在且为已认证则删除之前认证重新创建
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public ServiceReturnModel updateEnterpriseCertification(EnterpriseCertification enterpriseCertification) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if(enterpriseCertification!=null && enterpriseCertification.getUserId()>0) {
			int existCount=enterpriseCertificationDao.existByUserId(enterpriseCertification.getUserId());
			if(existCount>0) {
				//存在deleted为0的企业认证时，判断此认证的状态，待审核则不让修改，已认证则删除后重新添加，已拒绝则修改
				EnterpriseCertification current=enterpriseCertificationDao.findCurrentByUserId(enterpriseCertification.getUserId());
				if(current.getStatus().equals("ec_verify")) {
					returnValue.setResultCode(8);
					returnValue.setMsg("禁止修改");
				}else if (current.getStatus().equals("ec_pass")) {
					enterpriseCertificationDao.nullifyByUserId(enterpriseCertification.getUserId());
					enterpriseCertification.setId(0);
					enterpriseCertificationDao.insert(enterpriseCertification);
					if(enterpriseCertification.getId()>0) {
						returnValue.setResultCode(1);
						returnValue.setMsg("保存成功");
						returnValue.setResultObj(enterpriseCertification);
					}else {
						returnValue.setResultCode(6);
						returnValue.setMsg("保存失败");
					}
				}else if(current.getStatus().equals("ec_refuse")) {
					if(enterpriseCertificationDao.update(enterpriseCertification)) {
						returnValue.setResultCode(1);
						returnValue.setMsg("更新成功");
						returnValue.setResultObj(enterpriseCertification);
					}else {
						returnValue.setResultCode(7);
						returnValue.setMsg("更新失败");
					}
				}else {
					returnValue.setResultCode(-1);
					returnValue.setMsg("未知错误");
				}
			}else {
				enterpriseCertificationDao.insert(enterpriseCertification);
				if(enterpriseCertification.getId()>0) {
					returnValue.setResultCode(1);
					returnValue.setMsg("保存成功");
					returnValue.setResultObj(enterpriseCertification);
				}else {
					returnValue.setResultCode(6);
					returnValue.setMsg("保存失败");
				}
			}
		}else {
			returnValue.setResultCode(3);
			returnValue.setMsg("参数错误");
		}
		return returnValue;
	}

}
