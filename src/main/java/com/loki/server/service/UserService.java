package com.loki.server.service;

import java.util.HashMap;

public interface UserService {
	HashMap<String,Object> loginCheck(String phone,String password,String clientIp,String clientType);
	HashMap<String,Object> loginCheckByToken(String userToken);
	HashMap<String,Object> regist(String phone,String password,String authCode,int authCodeId,String clientIp,String clientType);
	HashMap<String,Object> getUser(int user);
	HashMap<String,Object> updateNickName(int userId,String nickName);
	HashMap<String,Object> updateAvatar(int userId,String avatar);
	HashMap<String,Object> updatePhone(int userId,String phone,String authCode,int authCodeId);
}
