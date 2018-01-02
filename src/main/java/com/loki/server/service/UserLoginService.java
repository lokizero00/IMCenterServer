package com.loki.server.service;


import com.loki.server.complexModel.ServiceReturnModel;

public interface UserLoginService {
	ServiceReturnModel loginCheck(String userName,String password,String clientIP,String clientType);
	ServiceReturnModel loginCheckByToken(String userToken);
	ServiceReturnModel regist(String phone,String password,String clientIP,String clientType);
}
