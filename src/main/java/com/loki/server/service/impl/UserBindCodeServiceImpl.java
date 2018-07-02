package com.loki.server.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.UserBindCodeDao;
import com.loki.server.entity.UserBindCode;
import com.loki.server.service.UserBindCodeService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.SendSmsUtil;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class UserBindCodeServiceImpl implements UserBindCodeService {
	@Resource UserBindCodeDao userBindCodeDao;

	@Override
	public ServiceResult<Integer> sendSmsAuthCode(String phone) {
		ServiceResult<Integer> returnValue=new ServiceResult<Integer>();
		if(phone!=null && !(phone.equals(""))) {
			String authCode=CommonUtil.getInstance().getRandomStr(4, 0);
			UserBindCode ubc=new UserBindCode();
			ubc.setAuthCode(authCode);
			userBindCodeDao.insert(ubc);
			if(ubc.getId()>0) {
				if(SendSmsUtil.getInstance().sendAuthCode(phone, authCode, ubc.getId())) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(ubc.getId());
				}else {
					returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_SEND_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> checkAuthCode(int authCodeId, String authCode) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		if (authCodeId>0 && authCode!=null && !(authCode.equals(""))) {
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
