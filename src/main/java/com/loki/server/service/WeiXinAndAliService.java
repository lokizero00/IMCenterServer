package com.loki.server.service;

import java.math.BigDecimal;

import com.loki.server.dto.RechargeDTO;
import com.loki.server.entity.IntentionRefund;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

public interface WeiXinAndAliService {
	/**
     * 下单接口，包含-充值下单、押金下单
     * @param outTradeNo    商户号
     * @param totalAmount   金额
     * @param requestHost   请求主机IP
     * @param payType       支付方式    1：支付宝、2：微信
     * @throws BizException
     */
    public ServiceResult<RechargeDTO> unifiedOrder(String outTradeNo, BigDecimal totalAmount, String requestHost, int payType) throws ServiceException;
    
    /**
     * 关闭订单接口
     * @param outTradeNo    商户号
     * @param payType       支付方式    1：支付宝、2：微信
     * @throws BizException
     */
    public String closeOrder(String outTradeNo,int payType) throws ServiceException ;
    
    public ServiceResult<String> awakenAliAuthInfoStr();
    
    public ServiceResult<Void> getAlipayAccount(String authCode, int userId) ;
    public ServiceResult<Void> getWxOpenid(String code, int userId) ;
    
    public void refund(IntentionRefund intentionRefund,int adminPayerId) throws ServiceException;
}
