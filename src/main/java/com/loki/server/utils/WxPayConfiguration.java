package com.loki.server.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.lang3.StringUtils;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

@Configuration
public class WxPayConfiguration {
    @Bean
    public WxPayConfig config() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(WxPayProperties.getInstance().getAppId());
        payConfig.setMchId(WxPayProperties.getInstance().getMchId());
        payConfig.setMchKey(WxPayProperties.getInstance().getMchKey());
        payConfig.setSubAppId(StringUtils.trimToNull(WxPayProperties.getInstance().getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(WxPayProperties.getInstance().getSubMchId()));
        payConfig.setKeyPath(WxPayProperties.getInstance().getKeyPath());
        payConfig.setNotifyUrl(WxPayProperties.getInstance().getNotifyUrl());
        return payConfig;
    }

    @Bean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
