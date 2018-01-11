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
			if(null==user) {
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
}
