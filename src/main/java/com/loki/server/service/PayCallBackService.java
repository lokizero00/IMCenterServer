package com.loki.server.service;

import com.loki.server.dto.WxpayCallbackDTO;

public interface PayCallBackService {
	public String alipayCallback(String payNotifyInfo);
	public WxpayCallbackDTO wxpayCallback(String xmlData);
}
