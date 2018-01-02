package com.loki.server.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.complexModel.ServiceReturnModel;
import com.loki.server.complexModel.UserComplex;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dao.UserTokenDao;
import com.loki.server.model.Intention;
import com.loki.server.model.User;
import com.loki.server.model.UserToken;
import com.loki.server.service.UserLoginService;
import com.loki.server.utils.MD5;

@Service
@Transactional
public class UserLoginServiceImpl implements UserLoginService {
	
	@Resource private UserDao userDao;
	@Resource private UserTokenDao userTokenDao;
	@Resource private IntentionDao intentionDao;
	

	@Override
	public ServiceReturnModel loginCheck(String userName, String password,String clientIP,String clientType) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if(userName!=null && userName!="" && password!=null && password!="") {
			//用户登录验证
			User user=userDao.loginCheck(userName, password);
			if (user!=null) {
				//使旧的令牌过期
				userTokenDao.expireByUserId(user.getId());
				
				//写入新的登录令牌
				String token=user.getUserName()+System.currentTimeMillis();
				token=MD5.getMD5Str(token);
				UserToken userToken=new UserToken();
				userToken.setUserId(user.getId());
				userToken.setToken(token);
				userToken.setLoginIp(clientIP);
				userToken.setClientType(clientType);
				userToken.setExpired(false);
				userTokenDao.insert(userToken);
				
				//返回登录信息
				UserComplex userComplex=new UserComplex();
				userComplex.setUser(user);
				userComplex.setUserToken(userToken);
				
				returnValue.setResultCode(1);
				returnValue.setResultObj(userComplex);
				return returnValue;
			}else {
				returnValue.setResultCode(2);
				returnValue.setMsg("用户不存在");
				return returnValue;
			}
		}
		returnValue.setResultCode(3);
		returnValue.setMsg("参数错误");
		return returnValue;
	}

	@Override
	public ServiceReturnModel loginCheckByToken(String token) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if (token!=null && token!="") {
			//用户令牌验证
			UserToken userToken=userTokenDao.findByToken(token);
			if (userToken!=null) {
				//获取用户信息
				User user=userDao.findById(userToken.getUserId());
				
				//返回登录信息
				UserComplex userComplex=new UserComplex();
				userComplex.setUser(user);
				userComplex.setUserToken(userToken);
				
				returnValue.setResultCode(1);
				returnValue.setResultObj(userComplex);
				return returnValue;
			}else {
				returnValue.setResultCode(4);
				returnValue.setMsg("登录令牌已失效");
				return returnValue;
			}
		}
		returnValue.setResultCode(3);
		returnValue.setMsg("参数错误");
		return returnValue;
	}

	@Override
	public ServiceReturnModel regist(String phone,String password,String clientIP,String clientType) {
		ServiceReturnModel returnValue=new ServiceReturnModel();
		if (phone!=null && phone!="" && password!=null && password!="") {
			int uCount=userDao.userExistCheck(phone);
			if (uCount>0) {
				returnValue.setResultCode(5);
				returnValue.setMsg("手机号已存在");
				return returnValue;
			}
			//注册用户
			User user=new User();
			String md5Password=MD5.getMD5Str(password);
			String userName="imade_"+phone;
			user.setUserName(userName);
			user.setPassword(md5Password);
			user.setNickName(phone);
			user.setPhone(phone);
			user.setPhoneBind(true);
			user.setRegistIp(clientIP);
			user.setEaseId(user.getUserName());
			user.setEasePwd(md5Password);
			userDao.insert(user);
			
			//写入登录令牌
			String token=user.getUserName()+System.currentTimeMillis();
			token=MD5.getMD5Str(token);
			UserToken userToken=new UserToken();
			userToken.setUserId(user.getId());
			userToken.setToken(token);
			userToken.setLoginIp(clientIP);
			userToken.setClientType(clientType);
			userToken.setExpired(false);
			userTokenDao.insert(userToken);
			
			//创建意向金账户
			Intention intention=new Intention();
			intention.setTotal(BigDecimal.ZERO);
			intention.setAvailable(BigDecimal.ZERO);
			intention.setFreeze(BigDecimal.ZERO);
			intention.setUserId(user.getId());
			intentionDao.insert(intention);
			
			//返回登录信息
			UserComplex userComplex=new UserComplex();
			userComplex.setUser(user);
			userComplex.setUserToken(userToken);
			
			returnValue.setResultCode(1);
			returnValue.setResultObj(userComplex);
			return returnValue;
		}
		returnValue.setResultCode(3);
		returnValue.setMsg("参数错误");
		return returnValue;
	}

}
