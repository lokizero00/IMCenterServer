package com.loki.server.controller;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.loki.server.dto.WxpayCallbackDTO;
import com.loki.server.service.PayCallBackService;

@Controller
@RequestMapping("/payNotify")
public class PayNotifyController {
	@Autowired
	PayCallBackService payCallBackService;
	
	protected Logger logger = Logger.getLogger(PayNotifyController.class);

	/**
	 * 支付宝支付统一下单回调接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/unified/alipay", method = RequestMethod.POST)
	@ResponseBody
	public String alipayCallback(HttpServletRequest request) {
		logger.debug("支付宝回调接口");
		String result = "fail";
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		logger.debug("支付宝回调参数===" + params);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String payNotifyInfo = objectMapper.writeValueAsString(params);
			result = payCallBackService.alipayCallback(payNotifyInfo);

		} catch (Exception e) {
			logger.error("支付宝回调异常：" + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 微信支付统一下单回调接口
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/unified/wxpay", method = RequestMethod.POST)
	@ResponseBody
	public String wxpayCallback(HttpServletRequest request) {
		logger.debug("微信回调接口");
		WxpayCallbackDTO wxpayCallbackDto = new WxpayCallbackDTO();
		wxpayCallbackDto.setReturn_code(WxPayConstants.ResultCode.FAIL);
		try {
			BufferedReader reader = request.getReader();
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			request.getReader().close();
			String xmlData = sb.toString();

			logger.debug("接收到的报文：" + xmlData);
			if (StringUtils.isEmpty(xmlData)) {
				wxpayCallbackDto.setReturn_msg("参数格式校验错误");
			} else {
				wxpayCallbackDto = payCallBackService.wxpayCallback(xmlData);

			}
		} catch (Exception e) {
			logger.error("微信回调异常：" + e.getMessage());
			wxpayCallbackDto.setReturn_msg("微信回调异常");
		}
		return wxpayCallbackDto.toString();
	}
}
