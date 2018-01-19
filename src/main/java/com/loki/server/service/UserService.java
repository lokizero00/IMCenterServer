package com.loki.server.service;

import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserLoginVO;
import com.loki.server.vo.UserVO;

public interface UserService {
	ServiceResult<UserLoginVO> loginCheck(String phone,String password,String clientIp,String clientType);
	ServiceResult<UserLoginVO> loginCheckByToken(String userToken);
	ServiceResult<UserLoginVO> regist(String phone,String password,String authCode,int authCodeId,String clientIp,String clientType);
	ServiceResult<UserVO> getUser(int user);
	ServiceResult<Void> updateNickName(int userId,String nickName);
	ServiceResult<Void> updateAvatar(int userId,String avatar);
	ServiceResult<Void> updatePhone(int userId,String phone,String authCode,int authCodeId);
}
