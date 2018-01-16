package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.User;
import com.loki.server.service.EnterpriseCertificationService;
import com.loki.server.utils.ResultCodeEnums;

@Service
@Transactional
public class EnterpriseCertificationServiceImpl implements EnterpriseCertificationService{
	@Resource EnterpriseCertificationDao enterpriseCertificationDao;
	@Resource UserDao userDao;
	
	//企业认证
	//不存在则创建，存在且为已拒绝则更新，存在且为已认证则删除之前认证重新创建
	//TODO 逻辑中使用了太多的if，后续优化
	@Override
	public ServiceResult<EnterpriseCertification> updateEnterpriseCertification(int userId,String position,String enterpriseName,String licensePic) {
		ServiceResult<EnterpriseCertification> returnValue=new ServiceResult<EnterpriseCertification>();
		if(userId>0) {
			EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findCurrentByUserId(userId);
			if(null!=enterpriseCertification) {
				//存在deleted为0的企业认证时，判断此认证的状态，待审核则不让修改，已认证则删除后重新添加，已拒绝则修改
				if(enterpriseCertification.getStatus().equals("ec_verify")) {
					returnValue.setResultCode(ResultCodeEnums.NOT_ALLOW_EDIT);
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
						
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(enterpriseCertification);
					}else {
						returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
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
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(enterpriseCertification);
					}else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
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
					
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(enterpriseCertification);
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
	public ServiceResult<EnterpriseCertification> getEnterpriseCertification(int userId) {
		ServiceResult<EnterpriseCertification> returnValue=new ServiceResult<EnterpriseCertification>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findById(user.getEnterpriseId());
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(enterpriseCertification);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
