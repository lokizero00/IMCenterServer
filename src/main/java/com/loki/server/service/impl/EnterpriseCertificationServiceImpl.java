package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.UserDao;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.User;
import com.loki.server.service.EnterpriseCertificationService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.EnterpriseCertificationVO;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class EnterpriseCertificationServiceImpl implements EnterpriseCertificationService{
	@Resource EnterpriseCertificationDao enterpriseCertificationDao;
	@Resource UserDao userDao;
	
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

	@Override
	public ServiceResult<Integer> addEnterpriseCertification(EnterpriseCertificationVO enterpriseCertificationVO) {
		ServiceResult<Integer> returnValue=new ServiceResult<>();
		if(enterpriseCertificationVO!=null && enterpriseCertificationVO.getUserId()>0) {
			EnterpriseCertification lastCertification=enterpriseCertificationDao.findCurrentByUserId(enterpriseCertificationVO.getUserId());
			if(lastCertification==null || lastCertification.getStatus().equals("ec_pass")) {
				if(lastCertification!=null) {
					if(!enterpriseCertificationDao.nullifyByUserId(enterpriseCertificationVO.getUserId())) {
						returnValue.setResultCode(ResultCodeEnums.DELETE_FAIL);
						return returnValue;
					}
				}
				EnterpriseCertification newCertification=new EnterpriseCertification();
				newCertification.setCreatorId(enterpriseCertificationVO.getUserId());
				newCertification.setUserId(enterpriseCertificationVO.getUserId());
				newCertification.setPosition(enterpriseCertificationVO.getPosition());
				newCertification.setEnterpriseName(enterpriseCertificationVO.getEnterpriseName());
				newCertification.setLicensePic(enterpriseCertificationVO.getLicensePic());
				newCertification.setStatus("ec_verify");
				enterpriseCertificationDao.insert(newCertification);
				if(newCertification.getId()>0) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(newCertification.getId());
				}else {
					returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> editEnterpriseCertification(EnterpriseCertificationVO enterpriseCertificationVO) {
		ServiceResult<Void> returnValue=new ServiceResult<>();
		if(enterpriseCertificationVO!=null && enterpriseCertificationVO.getId()>0 && enterpriseCertificationVO.getUserId()>0) {
			EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findCurrentByUserId(enterpriseCertificationVO.getUserId());
			if(enterpriseCertification!=null) {
				if(enterpriseCertification.getId()==enterpriseCertificationVO.getId() && enterpriseCertification.getStatus().equals("ec_refuse")) {
					enterpriseCertification.setUpdaterId(enterpriseCertificationVO.getUserId());
					enterpriseCertification.setPosition(enterpriseCertificationVO.getPosition());
					enterpriseCertification.setEnterpriseName(enterpriseCertificationVO.getEnterpriseName());
					enterpriseCertification.setLicensePic(enterpriseCertificationVO.getLicensePic());
					enterpriseCertification.setVerifyTime(null);
					enterpriseCertification.setAdminVerifierId(0);
					enterpriseCertification.setStatus("ec_verify");
					enterpriseCertification.setRefuseReason("");
					if(enterpriseCertificationDao.update(enterpriseCertification)) {
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
