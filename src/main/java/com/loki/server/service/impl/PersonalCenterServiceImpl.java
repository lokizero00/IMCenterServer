package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dto.UserHideInfoDTO;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.Intention;
import com.loki.server.entity.User;
import com.loki.server.service.PersonalCenterService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.PersonalCenterVO;
import com.loki.server.vo.ServiceResult;

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
	
	@Override
	public ServiceResult<UserHideInfoDTO> getUserHideInfo(int userId) {
		ServiceResult<UserHideInfoDTO> returnValue=new ServiceResult<UserHideInfoDTO>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findById(user.getEnterpriseId());
				Intention intention=intentionDao.findByUserId(user.getId());
				UserHideInfoDTO userHideInfoDTO=new UserHideInfoDTO();
				
				if(identityCertification!=null && identityCertification.getStatus().equals("ic_pass")) {
					userHideInfoDTO.setTrueName(identityCertification.getTrueName().substring(0, 1) + "**(已认证)");
				}else {
					userHideInfoDTO.setTrueName("***(未认证)");
				}
				
				if(enterpriseCertification!=null && enterpriseCertification.getStatus().equals("ec_pass")) {
					userHideInfoDTO.setEnterpriseName(enterpriseCertification.getEnterpriseName().substring(0, 2) + "***********(已认证)");
					userHideInfoDTO.setPosition("***(已认证)");
				}else {
					userHideInfoDTO.setTrueName("***********(未认证)");
					userHideInfoDTO.setPosition("***(未认证)");
				}
				userHideInfoDTO.setPhone(user.getPhone().substring(0, 3) + "****" + user.getPhone().substring(7, user.getPhone().length()));
				
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(userHideInfoDTO);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
