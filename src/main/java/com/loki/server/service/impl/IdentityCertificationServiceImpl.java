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
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class IdentityCertificationServiceImpl implements IdentityCertificationService{
	@Resource IdentityCertificationDao identityCertificationDao;
	@Resource UserDao userDao;
	
	//不存在则创建，存在则更新
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public ServiceResult<IdentityCertification> updateIdentityCertification(int userId,String trueName,String identityNumber,String identityFront,String identityBack) {
		ServiceResult<IdentityCertification> returnValue=new ServiceResult<IdentityCertification>();
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
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(identityCertification);
					}else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.NOT_ALLOW_EDIT);
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
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(identityCertification);
				}else {
					returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
				}
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

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

}
