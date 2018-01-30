package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.UserDao;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.User;
import com.loki.server.service.IdentityCertificationService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.IdentityCertificationVO;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class IdentityCertificationServiceImpl implements IdentityCertificationService{
	@Resource IdentityCertificationDao identityCertificationDao;
	@Resource UserDao userDao;

	@Override
	public ServiceResult<IdentityCertification> getIdentityCertification(int userId) {
		ServiceResult<IdentityCertification> returnValue=new ServiceResult<IdentityCertification>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(identityCertification);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
	
	@Override
	public ServiceResult<String> getIdentityCertificationStatus(int userId) {
		ServiceResult<String> returnValue=new ServiceResult<String>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(user==null) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				IdentityCertification identityCertification=identityCertificationDao.findById(user.getIdentityId());
				if(null==identityCertification) {
					returnValue.setResultCode(ResultCodeEnums.IDENTITY_CERTIFICATION_NOT_EXIST);
				}else {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(identityCertification.getStatus());
				}
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Integer> addIdentityCertification(IdentityCertificationVO identityCertificationVO) {
		ServiceResult<Integer> returnValue=new ServiceResult<>();
		if(identityCertificationVO!=null && identityCertificationVO.getUserId()>0) {
			IdentityCertification identityCertification=new IdentityCertification();
			identityCertification.setCreatorId(identityCertificationVO.getUserId());
			identityCertification.setUserId(identityCertificationVO.getUserId());
			identityCertification.setTrueName(identityCertificationVO.getTrueName());
			identityCertification.setIdentityNumber(identityCertificationVO.getIdentityNumber());
			identityCertification.setIdentityFront(identityCertificationVO.getIdentityFront());
			identityCertification.setIdentityBack(identityCertificationVO.getIdentityBack());
			identityCertification.setStatus("ic_verify");
			identityCertificationDao.insert(identityCertification);
			if(identityCertification.getId()>0) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(identityCertification.getId());
			}else {
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> editIdentityCertification(IdentityCertificationVO identityCertificationVO) {
		ServiceResult<Void> returnValue=new ServiceResult<>();
		if(identityCertificationVO!=null && identityCertificationVO.getId()>0 && identityCertificationVO.getUserId()>0) {
			IdentityCertification identityCertification=identityCertificationDao.findByIdAndUserId(identityCertificationVO.getId(), identityCertificationVO.getUserId());
			if(identityCertification!=null) {
				if(identityCertification.getStatus().equals("ic_refuse")) {
					identityCertification.setUpdaterId(identityCertificationVO.getUserId());
					identityCertification.setTrueName(identityCertificationVO.getTrueName());
					identityCertification.setIdentityNumber(identityCertificationVO.getIdentityNumber());
					identityCertification.setIdentityFront(identityCertificationVO.getIdentityFront());
					identityCertification.setIdentityBack(identityCertificationVO.getIdentityBack());
					identityCertification.setVerifyTime(null);
					identityCertification.setAdminVerifierId(0);
					identityCertification.setStatus("ic_verify");
					identityCertification.setRefuseReason("");
					if(identityCertificationDao.update(identityCertification)) {
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					}else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

}
