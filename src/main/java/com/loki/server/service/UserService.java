package com.loki.server.service;

import com.loki.server.dto.ServiceResult;
import com.loki.server.entity.User;
import com.loki.server.vo.UserLoginVO;

public interface UserService {
	ServiceResult<UserLoginVO> loginCheck(String phone,String password,String clientIp,String clientType);
	ServiceResult<UserLoginVO> loginCheckByToken(String userToken);
	ServiceResult<UserLoginVO> regist(String phone,String password,String authCode,int authCodeId,String clientIp,String clientType);
	ServiceResult<User> getUser(int user);
	ServiceResult<User> updateNickName(int userId,String nickName);
	ServiceResult<User> updateAvatar(int userId,String avatar);
	ServiceResult<User> updatePhone(int userId,String phone,String authCode,int authCodeId);
}
