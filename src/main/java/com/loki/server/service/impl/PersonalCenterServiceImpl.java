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
	public ServiceReturnModel getPersonalData(int userId) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(user==null) {
				returnValue.setResultCode(2);
				returnValue.setMsg("用户不存在");
				return returnValue;
			}
			
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
			return returnValue;
		}
		returnValue.setResultCode(3);
		returnValue.setMsg("参数错误");
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
				return returnValue;
			}
			
			returnValue.setResultCode(1);
			returnValue.setResultObj(user);
			return returnValue;
		}
		returnValue.setResultCode(3);
		returnValue.setMsg("参数错误");
		return returnValue;
	}

}
