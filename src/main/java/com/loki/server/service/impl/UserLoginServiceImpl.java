package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public HashMap<String,Object> loginCheck(String userName, String password,String clientIP,String clientType) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
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
				HashMap<String,Object> userMap=new HashMap<String,Object>();
				userMap.put("user",user);
				userMap.put("userToken",userToken);
				
				returnValue.put("resultCode", 1);
				returnValue.put("resultObj",userMap);
			}else {
				returnValue.put("resultCode", 2);
				returnValue.put("msg","用户不存在");
			}
		}else {
			returnValue.put("resultCode", 3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String,Object> loginCheckByToken(String token) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (token!=null && token!="") {
			//用户令牌验证
			UserToken userToken=userTokenDao.findByToken(token);
			if (userToken!=null) {
				//获取用户信息
				User user=userDao.findById(userToken.getUserId());
				
				//返回登录信息
				HashMap<String,Object> userMap=new HashMap<String,Object>();
				userMap.put("user",user);
				userMap.put("userToken",userToken);
				
				returnValue.put("resultCode", 1);
				returnValue.put("resultObj",userMap);
			}else {
				returnValue.put("resultCode", 4);
				returnValue.put("msg","登录令牌已失效");
			}
		}else {
			returnValue.put("resultCode", 3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String,Object> regist(String phone,String password,String clientIP,String clientType) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (phone!=null && phone!="" && password!=null && password!="") {
			int uCount=userDao.userExistCheck(phone);
			if (uCount>0) {
				returnValue.put("resultCode", 5);
				returnValue.put("msg","手机号已存在");;
			}else {
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
				HashMap<String,Object> userMap=new HashMap<String,Object>();
				userMap.put("user",user);
				userMap.put("userToken",userToken);
				
				returnValue.put("resultCode", 1);
				returnValue.put("resultObj", userMap);
			}
		}else {
			returnValue.put("resultCode", 3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

}
