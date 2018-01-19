package com.loki.server.service.impl;

import java.math.BigDecimal;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.UserBindCodeDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dao.UserTokenDao;
import com.loki.server.entity.Intention;
import com.loki.server.entity.User;
import com.loki.server.entity.UserBindCode;
import com.loki.server.entity.UserToken;
import com.loki.server.service.UserService;
import com.loki.server.utils.BeanMapper;
import com.loki.server.utils.MD5;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserLoginVO;
import com.loki.server.vo.UserVO;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Resource UserDao userDao;
	@Resource private UserTokenDao userTokenDao;
	@Resource private IntentionDao intentionDao;
	@Resource private UserBindCodeDao userBindCodeDao;

	@Override
	public ServiceResult<UserLoginVO> loginCheck(String phone, String password,String clientIp,String clientType) {
		ServiceResult<UserLoginVO> returnValue=new ServiceResult<UserLoginVO>();
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
				UserLoginVO userLoginVO=new UserLoginVO();
				userLoginVO.setUser(user);
				userLoginVO.setUserToken(userToken);
				
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(userLoginVO);
			}else {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<UserLoginVO> loginCheckByToken(String token) {
		ServiceResult<UserLoginVO> returnValue=new ServiceResult<UserLoginVO>();
		if (token!=null && token!="") {
			//用户令牌验证
			UserToken userToken=userTokenDao.findByToken(token);
			if (userToken!=null) {
				//获取用户信息
				User user=userDao.findById(userToken.getUserId());
				
				//返回登录信息
				UserLoginVO userLoginVO=new UserLoginVO();
				userLoginVO.setUser(user);
				userLoginVO.setUserToken(userToken);
				
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(userLoginVO);
			}else {
				returnValue.setResultCode(ResultCodeEnums.TOKEN_EXPIRED);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<UserLoginVO> regist(String phone,String password,String authCode,int authCodeId,String clientIp,String clientType) {
		ServiceResult<UserLoginVO> returnValue=new ServiceResult<UserLoginVO>();
		if (null!=phone && ""!=phone && null!=password && ""!=password && authCodeId>0 && null!=authCode && ""!=authCode) {
			UserBindCode userBindCode=userBindCodeDao.findById(authCodeId);
			if(null!=userBindCode && authCode.equals(userBindCode.getAuthCode())) {
				//判断是否超过5分钟
				long codeTime=userBindCode.getSendTime().getTime();
				long nowTime=System.currentTimeMillis();
				if((nowTime-codeTime)<=300000) {
					int uCount=userDao.userExistCheck(phone);
					if (uCount>0) {
						returnValue.setResultCode(ResultCodeEnums.PHONE_EXISTS);
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
						UserLoginVO userLoginVO=new UserLoginVO();
						userLoginVO.setUser(user);
						userLoginVO.setUserToken(userToken);
						
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(userLoginVO);
					}
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
	
	@Override
	public ServiceResult<UserVO> getUser(int userId) {
		ServiceResult<UserVO> returnValue=new ServiceResult<UserVO>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				UserVO userVO=BeanMapper.map(user, UserVO.class);
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(userVO);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> updateNickName(int userId,String nickName) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				user.setNickName(nickName);
				if(userDao.update(user)) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				}else {
					returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
				}
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> updateAvatar(int userId, String avatar) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		if (userId>0) {
			User user=userDao.findById(userId);
			if(null==user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}else {
				user.setAvatar(avatar);
				if(userDao.update(user)) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				}else {
					returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
				}
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> updatePhone(int userId, String phone, String authCode, int authCodeId) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		if (userId>0 && authCodeId>0 && null!=phone && ""!=phone && null!=authCode && ""!=authCode) {
			UserBindCode userBindCode=userBindCodeDao.findById(authCodeId);
			if(null!=userBindCode && authCode.equals(userBindCode.getAuthCode())) {
				//判断是否超过5分钟
				long codeTime=userBindCode.getSendTime().getTime();
				long nowTime=System.currentTimeMillis();
				if((nowTime-codeTime)<=300000) {
					User user=userDao.findById(userId);
					if(null==user) {
						returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
					}else {
						user.setPhone(phone);
						user.setPhoneBind(true);
						if(userDao.update(user)) {
							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						}
						else {
							returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
						}
					}
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
