package com.loki.server.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.loki.server.dto.UserDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.User;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserLoginVO;

public interface UserService {
	//mobile
	ServiceResult<UserLoginVO> loginCheck(String phone,String password,String clientIp,String clientType);
	ServiceResult<UserLoginVO> loginCheckByToken(String userToken);
	ServiceResult<UserLoginVO> regist(String phone,String password,String authCode,int authCodeId,String clientIp,String clientType);
	ServiceResult<User> getUser_mobile(int userId);
	ServiceResult<Void> updateNickName(int userId,String nickName);
	ServiceResult<Void> updateAvatar(int userId,String avatar);
	ServiceResult<Void> updatePhone(int userId,String phone,String authCode,int authCodeId);
	ServiceResult<Void> findPassword(String phone,String newPassword,String authCode,int authCodeId);
	
	//web
	PagedResult<UserDTO> getUserList(Map<String,Object> map) throws ServiceException;
	UserDTO getUser(HttpServletRequest request,int userId) throws ServiceException;
	UserDTO userVerify(HttpServletRequest request,int userId,String status) throws ServiceException;
	UserDTO changeUserStatus(HttpServletRequest request,int userId) throws ServiceException;
	boolean changePassword(int userId,String newPassword) throws ServiceException;
	boolean changePayPwd(int userId,String newPayPwd) throws ServiceException; 
	UserDTO rebindPhone(HttpServletRequest request,int userId,String newPhone) throws ServiceException;
}
