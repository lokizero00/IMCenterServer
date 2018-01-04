package com.loki.server.service;


import java.util.HashMap;

public interface UserLoginService {
	HashMap<String,Object> loginCheck(String userName,String password,String clientIP,String clientType);
	HashMap<String,Object> loginCheckByToken(String userToken);
	HashMap<String,Object> regist(String phone,String password,String clientIP,String clientType);
}
