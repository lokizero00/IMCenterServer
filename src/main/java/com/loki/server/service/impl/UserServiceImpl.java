package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.UserBindCodeDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dao.UserTokenDao;
import com.loki.server.dto.UserDTO;
import com.loki.server.dto.convertor.UserConvertor;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.Intention;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.User;
import com.loki.server.entity.UserBindCode;
import com.loki.server.entity.UserToken;
import com.loki.server.service.UserService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.HuanXinUtil;
import com.loki.server.utils.MD5;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.utils.SessionContext;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserLoginVO;

import net.sf.json.JSONObject;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {

	@Resource
	UserDao userDao;
	@Resource
	private UserTokenDao userTokenDao;
	@Resource
	private IntentionDao intentionDao;
	@Resource
	private UserBindCodeDao userBindCodeDao;
	@Resource
	IdentityCertificationDao identityCertificationDao;
	@Resource
	EnterpriseCertificationDao enterpriseCertificationDao;

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Override
	public ServiceResult<UserLoginVO> loginCheck(String phone, String password, String clientIp, String clientType) {
		ServiceResult<UserLoginVO> returnValue = new ServiceResult<UserLoginVO>();
		try {
			if (phone != null && phone != "" && password != null && password != "") {
				String md5Password = MD5.getMD5Str(password);
				// 用户登录验证
				User user = userDao.loginCheck(phone, md5Password);
				if (user != null) {
					if (user.getStatus().equals("us_on")) {
						// 使旧的令牌过期
						userTokenDao.expireByUserId(user.getId());

						// 写入新的登录令牌
						String token = user.getUserName() + System.currentTimeMillis();
						token = MD5.getMD5Str(token);
						UserToken userToken = new UserToken();
						userToken.setUserId(user.getId());
						userToken.setToken(token);
						userToken.setLoginIp(clientIp);
						userToken.setClientType(clientType);
						userToken.setExpired(false);
						userTokenDao.insert(userToken);

						// 检查环信用户是否存在，不存在则创建
						HuanXinUtil hu = new HuanXinUtil();
						JSONObject json = hu.getHXUserInfo(user.getUserName());
						if (json == null) {
							int hxCode = hu.createUser(user.getUserName(), user.getPassword(), user.getNickName());
							if (hxCode != 200) {
								logger.error("用户 " + user.getUserName() + " 环信注册失败");
							}
						}

						// 返回登录信息
						UserLoginVO userLoginVO = new UserLoginVO();
						userLoginVO.setUser(user);
						userLoginVO.setUserToken(userToken);

						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(userLoginVO);
					} else {
						returnValue.setResultCode(ResultCodeEnums.USER_OUT_OF_SERVICE);
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<UserLoginVO> loginCheckByToken(String token) {
		ServiceResult<UserLoginVO> returnValue = new ServiceResult<UserLoginVO>();
		if (token != null && token != "") {
			// 用户令牌验证
			UserToken userToken = userTokenDao.findByToken(token);
			if (userToken != null) {
				// 获取用户信息
				User user = userDao.findById(userToken.getUserId());
				if (user != null) {
					if (user.getStatus().equals("us_on")) {
						// 返回登录信息
						UserLoginVO userLoginVO = new UserLoginVO();
						userLoginVO.setUser(user);
						userLoginVO.setUserToken(userToken);

						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(userLoginVO);
					} else {
						returnValue.setResultCode(ResultCodeEnums.USER_OUT_OF_SERVICE);
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.TOKEN_EXPIRED);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Transactional
	@Override
	public ServiceResult<UserLoginVO> regist(String phone, String password, String authCode, int authCodeId,
			String clientIp, String clientType) {
		ServiceResult<UserLoginVO> returnValue = new ServiceResult<UserLoginVO>();
		try {
			if (null != phone && "" != phone && null != password && "" != password && authCodeId > 0 && null != authCode
					&& "" != authCode) {
				UserBindCode userBindCode = userBindCodeDao.findById(authCodeId);
				if (null != userBindCode && authCode.equals(userBindCode.getAuthCode())) {
					// 判断是否超过5分钟
					long codeTime = userBindCode.getSendTime().getTime();
					long nowTime = System.currentTimeMillis();
					if ((nowTime - codeTime) <= 300000) {
						int uCount = userDao.userExistCheck(phone);
						if (uCount > 0) {
							returnValue.setResultCode(ResultCodeEnums.PHONE_EXISTS);
						} else {
							// 注册用户
							User user = new User();
							String md5Password = MD5.getMD5Str(password);
							String userName = "imade_" + phone;
							user.setUserName(userName);
							user.setPassword(md5Password);
							user.setNickName(phone);
							user.setPhone(phone);
							user.setPhoneBind(true);
							user.setRegistIp(clientIp);
							user.setEaseId(userName);
							user.setEasePwd(md5Password);
							user.setStatus("us_on");
							userDao.insert(user);

							// 写入登录令牌
							String token = user.getUserName() + System.currentTimeMillis();
							token = MD5.getMD5Str(token);
							UserToken userToken = new UserToken();
							userToken.setUserId(user.getId());
							userToken.setToken(token);
							userToken.setLoginIp(clientIp);
							userToken.setClientType(clientType);
							userToken.setExpired(false);
							userTokenDao.insert(userToken);

							// 创建意向金账户
							Intention intention = new Intention();
							intention.setTotal(BigDecimal.ZERO);
							intention.setAvailable(BigDecimal.ZERO);
							intention.setFreeze(BigDecimal.ZERO);
							intention.setUserId(user.getId());
							intentionDao.insert(intention);

							// 创建环信用户信息
							HuanXinUtil hu = new HuanXinUtil();
							int hxCode = hu.createUser(userName, md5Password, phone);
							if (hxCode != 200) {
								logger.error("用户 " + userName + " 环信注册失败");
							}

							// 返回登录信息
							UserLoginVO userLoginVO = new UserLoginVO();
							userLoginVO.setUser(user);
							userLoginVO.setUserToken(userToken);

							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
							returnValue.setResultObj(userLoginVO);
						}
					} else {
						returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_TIME_OUT);
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_WRONG);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<User> getUser_mobile(int userId) {
		ServiceResult<User> returnValue = new ServiceResult<User>();
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (user != null) {
				if (user.getStatus().equals("us_on")) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(user);
				} else {
					returnValue.setResultCode(ResultCodeEnums.USER_OUT_OF_SERVICE);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Transactional
	@Override
	public ServiceResult<Void> updateNickName(int userId, String nickName) {
		ServiceResult<Void> returnValue = new ServiceResult<Void>();
		try {
			if (userId > 0) {
				User user = userDao.findById(userId);
				if (null == user) {
					returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
				} else {
					user.setNickName(nickName);
					if (userDao.update(user)) {
						// 更新环信昵称
						HuanXinUtil hu = new HuanXinUtil();
						int hxCode = hu.changeUserNickname(user.getUserName(), nickName);
						if (hxCode != 200) {
							logger.error("用户 " + user.getUserName() + " 修改昵称失败");
						}
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					} else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> updateAvatar(int userId, String avatar) {
		ServiceResult<Void> returnValue = new ServiceResult<Void>();
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (null == user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			} else {
				user.setAvatar(avatar);
				if (userDao.update(user)) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				} else {
					returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
				}
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> updatePhone(int userId, String phone, String authCode, int authCodeId) {
		ServiceResult<Void> returnValue = new ServiceResult<Void>();
		if (userId > 0 && authCodeId > 0 && null != phone && "" != phone && null != authCode && "" != authCode) {
			UserBindCode userBindCode = userBindCodeDao.findById(authCodeId);
			if (null != userBindCode && authCode.equals(userBindCode.getAuthCode())) {
				// 判断是否超过5分钟
				long codeTime = userBindCode.getSendTime().getTime();
				long nowTime = System.currentTimeMillis();
				if ((nowTime - codeTime) <= 300000) {
					User user = userDao.findById(userId);
					if (null == user) {
						returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
					} else {
						user.setPhone(phone);
						user.setPhoneBind(true);
						if (userDao.update(user)) {
							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						} else {
							returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
						}
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_TIME_OUT);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_WRONG);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> findPassword(String phone, String newPassword, String authCode, int authCodeId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		if (phone != null && !(phone.equals("")) && newPassword != null && !(newPassword.equals("")) && authCode != null
				&& !(authCode.equals("")) && authCodeId > 0) {
			UserBindCode userBindCode = userBindCodeDao.findById(authCodeId);
			if (null != userBindCode && authCode.equals(userBindCode.getAuthCode())) {
				// 判断是否超过5分钟
				long codeTime = userBindCode.getSendTime().getTime();
				long nowTime = System.currentTimeMillis();
				if ((nowTime - codeTime) <= 300000) {
					User user = userDao.findByPhone(phone);
					if (user != null) {
						if (user.getStatus().equals("us_on")) {
							String newMd5Password = MD5.getMD5Str(newPassword);
							user.setPassword(newMd5Password);
							userDao.update(user);
							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						} else {
							returnValue.setResultCode(ResultCodeEnums.USER_OUT_OF_SERVICE);
						}
					} else {
						returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_TIME_OUT);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.AUTH_CODE_WRONG);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public PagedResult<UserDTO> getUserList(Map<String, Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<User> userList = userDao.findByParam(map);
			List<UserDTO> userDTOList = new ArrayList<>();
			for (User user : userList) {
				UserDTO userDTO = UserConvertor.convertUser2UserDTO(user);
				userDTO = setDTOExtendFields(userDTO, null);
				userDTOList.add(userDTO);
			}
			Page data = (Page) userList;
			PagedResult<UserDTO> pageResult = BeanUtil.toPagedResult(userDTOList, data.getPageNum(), data.getPageSize(),
					data.getTotal(), data.getPages());
			if (pageResult != null) {
				return pageResult;
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public UserDTO getUser(HttpServletRequest request, int userId) throws ServiceException {
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (user != null) {
				UserDTO userDTO = UserConvertor.convertUser2UserDTO(user);
				if (userDTO != null) {
					userDTO = setDTOExtendFields(userDTO, request);
					return userDTO;
				} else {
					throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public UserDTO userVerify(HttpServletRequest request, int userId, String status) throws ServiceException {
		// TODO web端用户审核
		return null;
	}

	@Override
	public UserDTO changeUserStatus(HttpServletRequest request, int userId) throws ServiceException {
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (user != null) {
				int adminId=(int) request.getSession().getAttribute("adminId");
				String loginIp=(String) request.getSession().getAttribute("loginIp");
				String adminLogContent = "管理员 " + getAdminName(adminId);
				if (user.getStatus().equals("us_on")) {
					adminLogContent += " 停用 ";
					user.setStatus("us_off");
				} else if (user.getStatus().equals("us_off")) {
					adminLogContent += " 启用 ";
					user.setStatus("us_on");
				} else {
					throw new ServiceException(ResultCodeEnums.DATA_INVALID);
				}
				adminLogContent += "了用户 " + user.getUserName() + " 的账户";
				if (userDao.update(user)) {
					addAdminLog(adminLogContent,adminId,loginIp);
					UserDTO userDTO = UserConvertor.convertUser2UserDTO(user);
					if (userDTO != null) {
						userDTO = setDTOExtendFields(userDTO, request);
						return userDTO;
					} else {
						throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
					}
				} else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	protected UserDTO setDTOExtendFields(UserDTO userDTO, HttpServletRequest request) {
		if (userDTO != null) {
			userDTO.setStatusName(getDictionariesValue("user_status", userDTO.getStatus()));
			userDTO.setIdentityStatusName("未认证");
			if (userDTO.getIdentityId() > 0) {
				String identityStatus = identityCertificationDao.findStatusById(userDTO.getIdentityId());
				if (identityStatus != null) {
					userDTO.setIdentityStatusName(
							getDictionariesValue("identity_certification_status", identityStatus));
				}
			}
			userDTO.setEnterpriseStatusName("未认证");
			if (userDTO.getEnterpriseId() > 0) {
				String enterpriseStatus = enterpriseCertificationDao.findStatusById(userDTO.getEnterpriseId());
				if (enterpriseStatus != null) {
					userDTO.setEnterpriseStatusName(
							getDictionariesValue("enterprise_certification_status", enterpriseStatus));
				}
			}
			if (request != null && userDTO.getAvatar() != null && !(userDTO.getAvatar().equals(""))) {
				userDTO.setAvatarUrl(getImageRequestUrl(request, userDTO.getAvatar()));
			}
			return userDTO;
		} else {
			return null;
		}
	}

	@Override
	public boolean changePassword(int adminId,String loginIp,int userId, String newPassword) throws ServiceException {
		if (userId > 0 && newPassword != null && !(newPassword.equals(""))) {
			User user = userDao.findById(userId);
			if (user != null) {
				String newMd5Password = MD5.getMD5Str(newPassword);
				user.setPassword(newMd5Password);
				if (userDao.update(user)) {
					addAdminLog("管理员 " + getAdminName(adminId) + " 修改了用户 " + user.getUserName() + " 的登录密码",adminId,loginIp);
					return true;
				} else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}
	
	@Override
	public boolean changePayPwd(int adminId,String loginIp,int userId, String newPayPwd) throws ServiceException {
		if (userId > 0 && newPayPwd != null && !(newPayPwd.equals(""))) {
			User user = userDao.findById(userId);
			if (user != null) {
				String newMd5PayPwd = MD5.getMD5Str(newPayPwd);
				user.setPayPwd(newMd5PayPwd);
				if (userDao.update(user)) {
					addAdminLog("管理员 " + getAdminName(adminId) + " 修改了用户 " + user.getUserName() + " 的支付密码",adminId,loginIp);
					return true;
				} else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public UserDTO rebindPhone(HttpServletRequest request,int adminId,String loginIp, int userId, String newPhone) throws ServiceException {
		if (userId > 0 && newPhone != null && !(newPhone.equals(""))) {
			User user = userDao.findById(userId);
			if (user != null) {
				user.setPhone(newPhone);
				if (userDao.update(user)) {
					addAdminLog("管理员 " + getAdminName(adminId) + " 重新绑定了 " + user.getUserName() + " 的手机号，新手机号为 "
							+ newPhone,adminId,loginIp);
					UserDTO userDTO = UserConvertor.convertUser2UserDTO(user);
					if (userDTO != null) {
						userDTO = setDTOExtendFields(userDTO, request);
						return userDTO;
					} else {
						throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
					}
				} else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	/**
	 * 通过环信id获取用户
	 * 
	 * @param easeId
	 * @return UserDTO
	 */
	@Override
	public ServiceResult<User> getUserByEaseId(String easeId) {
		ServiceResult<User> returnValue = new ServiceResult<>();
		try {
			if (easeId != null && easeId != "") {
				User user = userDao.findByEaseId(easeId);
				if (user != null) {
					returnValue.setResultObj(user);
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				} else {
					returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<String> getEaseNameByUserId(int userId) {
		ServiceResult<String> returnValue = new ServiceResult<>();
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (user != null) {
				String easeEnterprise = "企业未认证";
				String easeIdentity = "未实名用户";
				String easePhone = user.getPhone();
				String easeName = "";

				// 判断用户是否认证
				IdentityCertification ic = identityCertificationDao.findByUserId(user.getId());
				EnterpriseCertification ec = enterpriseCertificationDao.findCurrentByUserId(user.getId());
				if (ec != null && ec.getStatus().equals("ec_pass")) {
					easeName += ec.getEnterpriseName() + "-" + ic.getTrueName() + "-" + ec.getPosition();
				} else {
					if (ic != null && ic.getStatus().equals("ic_pass")) {
						easeName += easeEnterprise + "-" + ic.getTrueName();
					} else {
						easeName += easeIdentity + "-" + easePhone;
					}
				}

				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(easeName);
			} else {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
