package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.UserBindCodeDao;
import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.UserBindCode;
import com.loki.server.service.UserBindCodeService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.SendSmsUtil;

@Service
@Transactional
public class UserBindCodeServiceImpl implements UserBindCodeService {
	@Resource UserBindCodeDao userBindCodeDao;

	@Override
	public ServiceResult<UserBindCode> sendSmsAuthCode(String phone) {
		ServiceResult<UserBindCode> returnValue=new ServiceResult<UserBindCode>();
		if(null != phone && "" != phone) {
			String authCode=CommonUtil.getInstance().getRandomStr(6, 3);
			UserBindCode ubc=new UserBindCode();
			ubc.setAuthCode(authCode);
			userBindCodeDao.insert(ubc);
			if(ubc.getId()>0) {
				if(SendSmsUtil.getInstance().sendAuthCode(phone, authCode, ubc.getId())) {
					ubc.setAuthCode("");
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(ubc);
				}else {
					returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_SEND_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_SAVE_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<UserBindCode> checkAuthCode(int authCodeId, String authCode) {
		ServiceResult<UserBindCode> returnValue=new ServiceResult<UserBindCode>();
		if (authCodeId>0 && null!=authCode && ""!=authCode) {
			UserBindCode userBindCode=userBindCodeDao.findById(authCodeId);
			if(null!=userBindCode && authCode.equals(userBindCode.getAuthCode())) {
				//判断是否超过5分钟
				long codeTime=userBindCode.getSendTime().getTime();
				long nowTime=System.currentTimeMillis();
				if((nowTime-codeTime)<=300000) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				}else {
					returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_TIME_OUT);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_WRONG);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

}
