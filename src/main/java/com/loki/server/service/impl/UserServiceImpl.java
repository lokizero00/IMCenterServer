package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.UserBindCodeDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dao.UserTokenDao;
import com.loki.server.model.Intention;
import com.loki.server.model.User;
import com.loki.server.model.UserBindCode;
import com.loki.server.model.UserToken;
import com.loki.server.service.UserService;
import com.loki.server.utils.MD5;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Resource UserDao userDao;
	@Resource private UserTokenDao userTokenDao;
	@Resource private IntentionDao intentionDao;
	@Resource private UserBindCodeDao userBindCodeDao;

	@Override
	public HashMap<String,Object> loginCheck(String phone, String password,String clientIp,String clientType) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if(phone!=null && phone!="" && password!=null && password!="") {
			//用户登录验证
			User user=userDao.loginCheck(phone, password);
			if (user!=null) {
				//使旧的令牌过期
				userTokenDao.expireByUserId(user.getId());
				
				//写入新的登录令牌
				String token=user.getUserName()+System.currentTimeMillis();
				token=MD5.getMD5Str(token);
				UserToken userToken=new UserToken();
				userToken.setUserId(user.getId());
				userToken.setToken(token);
				userToken.setLoginIp(clientIp);
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
	public HashMap<String,Object> regist(String phone,String password,String authCode,int authCodeId,String clientIp,String clientType) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (null!=phone && ""!=phone && null!=password && ""!=password && authCodeId>0 && null!=authCode && ""!=authCode) {
			UserBindCode userBindCode=userBindCodeDao.findById(authCodeId);
			if(null!=userBindCode && authCode.equals(userBindCode.getAuthCode())) {
				//判断是否超过5分钟
				long codeTime=userBindCode.getSendTime().getTime();
				long nowTime=System.currentTimeMillis();
				if((nowTime-codeTime)<=300000) {
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
						user.setRegistIp(clientIp);
						user.setEaseId(user.getUserName());
						user.setEasePwd(md5Password);
						userDao.insert(user);
						
						//写入登录令牌
						String token=user.getUserName()+System.currentTimeMillis();
						token=MD5.getMD5Str(token);
						UserToken userToken=new UserToken();
						userToken.setUserId(user.getId());
						userToken.setToken(token);
						userToken.setLoginIp(clientIp);
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
					returnValue.put("resultCode", 12);
					returnValue.put("msg","验证码超时");
				}
			}else {
				returnValue.put("resultCode", 11);
				returnValue.put("msg","验证码错误");
			}
		}else {
			returnValue.put("resultCode", 3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}
	
	@Override
	public HashMap<String,Object> getUser(int userId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.put("resultCode",2);
				returnValue.put("msg","用户不存在");
			}else {
				returnValue.put("resultCode",1);
				returnValue.put("resultObj",user);
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String, Object> updateNickName(int userId,String nickName) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.put("resultCode",2);
				returnValue.put("msg","用户不存在");
			}else {
				user.setNickName(nickName);
				if(userDao.update(user)) {
					returnValue.put("resultCode",1);
					returnValue.put("msg", "更新成功");
					returnValue.put("resultObj",user);
				}else {
					returnValue.put("resultCode",7);
					returnValue.put("msg", "更新失败");
				}
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String, Object> updateAvatar(int userId, String avatar) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.put("resultCode",2);
				returnValue.put("msg","用户不存在");
			}else {
				user.setAvatar(avatar);
				if(userDao.update(user)) {
					returnValue.put("resultCode",1);
					returnValue.put("msg", "更新成功");
					returnValue.put("resultObj",user);
				}else {
					returnValue.put("resultCode",7);
					returnValue.put("msg", "更新失败");
				}
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String, Object> updatePhone(int userId, String phone, String authCode, int authCodeId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if (userId>0 && authCodeId>0 && null!=phone && ""!=phone && null!=authCode && ""!=authCode) {
			UserBindCode userBindCode=userBindCodeDao.findById(authCodeId);
			if(null!=userBindCode && authCode.equals(userBindCode.getAuthCode())) {
				//判断是否超过5分钟
				long codeTime=userBindCode.getSendTime().getTime();
				long nowTime=System.currentTimeMillis();
				if((nowTime-codeTime)<=300000) {
					User user=userDao.findById(userId);
					if(null==user) {
						returnValue.put("resultCode",2);
						returnValue.put("msg","用户不存在");
					}else {
						user.setPhone(phone);
						user.setPhoneBind(true);
						if(userDao.update(user)) {
							returnValue.put("resultCode", 1);
							returnValue.put("msg","更新成功");
							returnValue.put("resultObj", user);
						}
						else {
							returnValue.put("resultCode", 7);
							returnValue.put("msg","更新失败");
						}
					}
				}else {
					returnValue.put("resultCode", 12);
					returnValue.put("msg","验证码超时");
				}
			}else {
				returnValue.put("resultCode", 11);
				returnValue.put("msg","验证码错误");
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}	
}
