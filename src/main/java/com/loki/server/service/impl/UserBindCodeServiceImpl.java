package com.loki.server.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.UserBindCodeDao;
import com.loki.server.model.UserBindCode;
import com.loki.server.service.UserBindCodeService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.SendSmsUtil;

@Service
@Transactional
public class UserBindCodeServiceImpl implements UserBindCodeService {
	@Resource UserBindCodeDao userBindCodeDao;

	@Override
	public HashMap<String,Object> sendSmsAuthCode(String phone) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if(null != phone && "" != phone) {
			String authCode=CommonUtil.getInstance().getRandomStr(6, 3);
			UserBindCode ubc=new UserBindCode();
			ubc.setAuthCode(authCode);
			userBindCodeDao.insert(ubc);
			if(ubc.getId()>0) {
				if(SendSmsUtil.getInstance().sendAuthCode(phone, authCode, ubc.getId())) {
					ubc.setAuthCode("");
					returnValue.put("resultCode", 1);
					returnValue.put("resultObj",ubc);
				}else {
					returnValue.put("resultCode", 9);
					returnValue.put("msg","验证码发送失败");
				}
			}else {
				returnValue.put("resultCode", 10);
				returnValue.put("msg","验证码保存失败");
			}
		}else {
			returnValue.put("resultCode", 3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

}
