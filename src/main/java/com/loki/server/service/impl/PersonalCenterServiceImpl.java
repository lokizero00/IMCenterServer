package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.Intention;
import com.loki.server.entity.User;
import com.loki.server.service.PersonalCenterService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.PersonalCenterVO;

@Service
@Transactional
public class PersonalCenterServiceImpl implements PersonalCenterService {
	@Resource private UserDao userDao;
	@Resource private IdentityCertificationDao identityCertificationDao;
	@Resource private EnterpriseCertificationDao enterpriseCertificationDao;
	@Resource private IntentionDao intentionDao;

	@Override
	public ServiceResult<PersonalCenterVO> getPersonalCenter(int userId) {
		ServiceResult<PersonalCenterVO> returnValue=new ServiceResult<PersonalCenterVO>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findById(user.getEnterpriseId());
				Intention intention=intentionDao.findByUserId(user.getId());
				PersonalCenterVO personalCenterVO=new PersonalCenterVO();
				personalCenterVO.setUser(user);
				personalCenterVO.setIdentityCertification(identityCertification);
				personalCenterVO.setEnterpriseCertification(enterpriseCertification);
				personalCenterVO.setIntention(intention);
				
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(personalCenterVO);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
