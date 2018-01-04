package com.loki.server.service;

import java.util.HashMap;

public interface UserBindCodeService {
	HashMap<String,Object> sendSmsAuthCode(String phone);
}
