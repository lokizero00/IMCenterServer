package com.loki.server.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.google.gson.Gson;
import com.loki.server.dto.AliRechargeDTO;
import com.loki.server.dto.RechargeDTO;
import com.loki.server.dto.WXLoginDTO;
import com.loki.server.dto.WXUserInfoDTO;
import com.loki.server.dto.WxRechargeDTO;
import com.loki.server.service.WeiXinAndAliService;
import com.loki.server.utils.AliPayProperties;
import com.loki.server.utils.PayConst;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.utils.SpringUtils;
import com.loki.server.vo.ServiceResult;

@Service
public class WeiXinAndAliServiceImpl implements WeiXinAndAliService {

	private static final String SUCCESS = "SUCCESS";
	//
	private static final String WX_PACKAGE = "Sign=WXPay";
	//
	public static final String TRADE_TYPE = "APP";
	//
	// public static String APPID = "2018052760272101";
	//
	// public static final String SECRET = "08933835002d9a767aee303e4230dbeb";

	protected Logger logger = Logger.getLogger(WeiXinAndAliServiceImpl.class);

	@Resource(name = "wxPayService")
	private WxPayService wxPayService;

	@Override
	public ServiceResult<RechargeDTO> unifiedOrder(String outTradeNo, BigDecimal totalAmount, String requestHost,
			int payType) throws ServiceException {
		// 支付宝返回对象
		AliRechargeDTO aliRechargeDTO = null;
		WxRechargeDTO wxRechargeDTO = null;
		switch (payType) {
		case 1: // 支付宝
			aliRechargeDTO = new AliRechargeDTO();
			AliPayProperties baseAliProperties = new AliPayProperties();
			String orderInfo = unifiedOrderToAli(baseAliProperties, outTradeNo, totalAmount, requestHost);
			aliRechargeDTO.setOrderInfo(orderInfo);
			break;
		case 2: // 微信
			WxPayUnifiedOrderResult result = unfiedOrderToWx(wxPayService, outTradeNo, totalAmount, requestHost);

			Map hashMap = new HashMap();
			hashMap.put("appid", result.getAppid());
			hashMap.put("partnerid", result.getMchId());
			hashMap.put("prepayid", result.getPrepayId());
			hashMap.put("package", WX_PACKAGE);
			hashMap.put("noncestr", result.getNonceStr());
			hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
			String sign = SignUtils.createSign(hashMap, WxPayConstants.SignType.MD5,
					wxPayService.getConfig().getMchKey(), false);

			wxRechargeDTO = new WxRechargeDTO();
			wxRechargeDTO.setAppid(result.getAppid());
			wxRechargeDTO.setNoncestr(result.getNonceStr());
			wxRechargeDTO.setPartnerid(result.getMchId());
			wxRechargeDTO.setPrepayid(result.getPrepayId());
			wxRechargeDTO.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
			wxRechargeDTO.setWxpackage(WX_PACKAGE);
			wxRechargeDTO.setSign(sign);
			break;
		}

		RechargeDTO rechargeDTO = new RechargeDTO();
		rechargeDTO.setAliRechargeDTO(aliRechargeDTO);
		rechargeDTO.setWxRechargeDTO(wxRechargeDTO);
		rechargeDTO.setPayType(payType);
		ServiceResult<RechargeDTO> result = new ServiceResult<>();

		result.setResultCode(ResultCodeEnums.SUCCESS);
		result.setResultObj(rechargeDTO);

		return result;
	}

	/**
	 * 微信-下单
	 * 
	 * @param wxPayService
	 * @param outTradeNo
	 * @param totalAmount
	 * @param requestHost
	 * @return
	 */
	private WxPayUnifiedOrderResult unfiedOrderToWx(WxPayService wxPayService, String outTradeNo,
			BigDecimal totalAmount, String requestHost) {
		if (null == totalAmount || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException(ResultCodeEnums.TRANACTION_ERROR_ZERO_AMOUNT);
		}

		WxPayUnifiedOrderRequest wxPayUnifiedOrder = new WxPayUnifiedOrderRequest();
		// 商品描述
		wxPayUnifiedOrder.setBody("充值");
		// 商户订单号
		wxPayUnifiedOrder.setOutTradeNo(outTradeNo);
		// 将单位元转换为分
		totalAmount = totalAmount.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
		// 标价金额
		wxPayUnifiedOrder.setTotalFee(totalAmount.intValue());
		// 终端IP
		wxPayUnifiedOrder.setSpbillCreateIp(requestHost);
		// 设置回调URL
		try {
			wxPayUnifiedOrder.setNotifyURL(wxPayService.getConfig().getNotifyUrl());
		} catch (Exception e) {
			throw new ServiceException(ResultCodeEnums.TRANACTION_NOTIFY_ERROR);
		}
		// 交易类型：JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
		wxPayUnifiedOrder.setTradeType(TRADE_TYPE);
		try {
			WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrder);
			checkResultByWeixin(wxPayUnifiedOrderResult);
			return wxPayUnifiedOrderResult;
		} catch (WxPayException e) {
			throw new ServiceException(ResultCodeEnums.TRANACTION_UNKNOW_ERROR);
		}
	}

	/**
	 * 支付宝-下单
	 * 
	 * @param outTradeNo
	 * @param totalAmount
	 * @param requestHost
	 * @return
	 */
	private String unifiedOrderToAli(AliPayProperties depProperties, String outTradeNo, BigDecimal totalAmount,
			String requestHost) {
		if (null == totalAmount || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException(ResultCodeEnums.TRANACTION_ERROR_ZERO_AMOUNT);
		}

		String result = "";
		AlipayClient alipayClient = new DefaultAlipayClient(depProperties.getGatewayUrl(), depProperties.getAppId(),
				depProperties.getAppPrivateKey(), PayConst.FORMAT, PayConst.CHARSET, depProperties.getAppPublicKey(),
				PayConst.SIGN_TYPE);

		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setSubject(depProperties.getSubject());
		model.setOutTradeNo(outTradeNo);
		model.setTotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		model.setProductCode(PayConst.PRODUCT_CODE);
		request.setBizModel(model);
		request.setNotifyUrl(depProperties.getNotifyUrl());
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.debug(mapper.writeValueAsString(request));
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			result = response.getBody();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		logger.debug("支付宝获取订单结果===" + result);
		return result;
	}

	/**
	 * 校验微信返回参数是否正常
	 * 
	 * @param wxPayBaseResult
	 * @return
	 */
	protected WxPayBaseResult checkResultByWeixin(WxPayBaseResult wxPayBaseResult) throws ServiceException {
		if (SUCCESS.equals(wxPayBaseResult.getReturnCode())) {
			if (SUCCESS.equals(wxPayBaseResult.getResultCode())) {
				return wxPayBaseResult;
			} else {
				throw new ServiceException(ResultCodeEnums.TRANACTION_WEIXIN_RETURN_ERROR);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	/**
	 * 校验支付宝参数是否正确
	 * 
	 * @param alipayResponse
	 * @throws BizException
	 */
	protected void checkResultByAli(AlipayResponse alipayResponse) throws ServiceException {
		if (null == alipayResponse) {
			throw new ServiceException(ResultCodeEnums.TRANACTION_ALI_RETURN_ERROR);
		}

		if (!alipayResponse.isSuccess()) {
			throw new ServiceException(ResultCodeEnums.TRANACTION_ALI_RETURN_ERROR);
		}
	}

	/**
	 * 关闭支付宝订单
	 * 
	 * @param outTradeNo
	 * @return
	 */
	public AlipayTradeCloseResponse closeAli(AliPayProperties depProperties, String outTradeNo) {
		AlipayClient alipayClient = new DefaultAlipayClient(depProperties.getGatewayUrl(), depProperties.getAppId(),
				depProperties.getAppPrivateKey(), PayConst.FORMAT, PayConst.CHARSET, depProperties.getAliPayPublicKey(),
				PayConst.SIGN_TYPE);
		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		AlipayTradeCloseResponse response = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> requestMap = new HashMap<>();
			requestMap.put("out_trade_no", outTradeNo);
			request.setBizContent(mapper.writeValueAsString(requestMap));
			response = alipayClient.execute(request);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new ServiceException(ResultCodeEnums.TRANACTION_ALI_CLOSE_ERROR);
		} catch (AlipayApiException e) {
			e.printStackTrace();
			throw new ServiceException(ResultCodeEnums.TRANACTION_ALI_CLOSE_ERROR);
		}

		checkResultByAli(response);
		return response;
	}

	// /**
	// * 关闭微信订单
	// * @param outTradeNo 商户订单号
	// * @return
	// * @throws BizException
	// */
	// public WxPayOrderCloseResult closeWx(WxPayService wxPayService,String
	// outTradeNo) throws ServiceException {
	// WxPayOrderCloseResult wxPayOrderCloseResult = null;
	//
	//// WxPayOrderCloseRequest wxPayOrderCloseRequest = new
	// WxPayOrderCloseRequest();
	//// wxPayOrderCloseRequest.setOutTradeNo(outTradeNo);
	// try {
	// wxPayOrderCloseResult = wxPayService.closeOrder(outTradeNo);
	// } catch (WxPayException e) {
	// e.printStackTrace();
	// throw new ServiceException(ResultCodeEnums.TRANACTION_WEIXIN_CLOSE_ERROR);
	// }
	//
	// checkResultByWeixin(wxPayOrderCloseResult);
	// return wxPayOrderCloseResult;
	// }

	// /**
	// * 支付宝-获取账户信息
	// */
	// private AliPayProperties aliUnifiedOrder(){
	// return SpringUtils.getBean(PayConst.ALI_CONFIG_NAME,AliPayProperties.class);
	// }

	// @Transactional
	// public ServiceResult<String> wxLoginAuth(String code) throws IOException {
	// ServiceResult<String> resultDO = new ServiceResult<>();
	// String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
	// APPID +
	// "&secret=" + SECRET +
	// "&code=" + code +
	// "&grant_type=authorization_code";
	// CloseableHttpClient httpClient = HttpClients.createDefault();
	// HttpGet httpGet = new HttpGet(url);
	// CloseableHttpResponse response = httpClient.execute(httpGet);
	// HttpEntity entity = response.getEntity();
	// ObjectMapper objectMapper = new ObjectMapper();
	// WXLoginDTO wxLoginDto = null;
	// if(entity != null){
	// String result = EntityUtils.toString(entity,"UTF-8");
	// wxLoginDto = objectMapper.readValue(result, WXLoginDTO.class);
	// if(wxLoginDto.getErrcode() == null){
	//
	// }
	// resultDO.setResultCode(ResultCodeEnums.SUCCESS);
	// resultDO.setResultObj(wxLoginDto.getAccess_token());
	// }
	// url = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
	// wxLoginDto.getAccess_token() +
	// "&openid=" + wxLoginDto.getOpenid();
	// httpGet = new HttpGet(url);
	// response = httpClient.execute(httpGet);
	// entity = response.getEntity();
	// String result = EntityUtils.toString(entity,"UTF-8");
	// Gson gson = new Gson();
	// WXUserInfoDTO wxUserInfoDto = gson.fromJson(result, WXUserInfoDTO.class);
	//
	// return resultDO;
	// }

	// /**
	// * 微信退款
	// * @param wxPayService
	// * @param innerBusino
	// * @param amount
	// * @return
	// */
	// public WxPayRefundResult wxReturn(WxPayService wxPayService,BigDecimal
	// total,String innerBusino,BigDecimal amount){
	// WxPayRefundResult wxPayRefundResult = null;
	//
	// WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
	// wxPayRefundRequest.setOutTradeNo(innerBusino);
	// //随机生成退款单号
	// String refundNo =
	// OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.RETURN.getKey());
	// wxPayRefundRequest.setOutRefundNo(refundNo);
	// if (null == amount || amount.compareTo(BigDecimal.ZERO) <= 0){
	// throw new BizException("","退款失败，退款金额为0元");
	// }
	// //将单位元转换为分
	// BigDecimal money=null;
	// money = amount.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
	// BigDecimal totalMoney=null;
	// totalMoney = total.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
	// wxPayRefundRequest.setTotalFee(totalMoney.intValue());
	// wxPayRefundRequest.setRefundFee(money.intValue());
	// try {
	// wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
	// } catch (WxPayException e) {
	// BizException bizException = new BizException();
	// bizException.setDynamicMsg(e.getErrCode(),e.getErrCodeDes());
	// throw bizException;
	// }
	// checkResultByWeixin(wxPayRefundResult);
	// return wxPayRefundResult;
	// }

	@Override
	public String closeOrder(String outTradeNo, int payType) throws ServiceException {
		String thirdTransNo = null;
		switch (payType) {
		case 1:
			AliPayProperties baseAliProperties = aliUnifiedOrder(); // 获取押金对象
			AlipayTradeCloseResponse alipayTradeCloseResponse = closeAli(baseAliProperties, outTradeNo);
			thirdTransNo = alipayTradeCloseResponse.getTradeNo();
			break;
		case 2:
			// WxPayOrderCloseResult wxPayOrderCloseResult =
			// closeWx(wxPayService,outTradeNo);
			// thirdTransNo = wxPayOrderCloseResult.getResultMsg();
			// break;
		}
		return thirdTransNo;
	}

	/**
	 * 支付宝-获取账户信息
	 */
	private AliPayProperties aliUnifiedOrder() {
		return SpringUtils.getBean(PayConst.ALI_CONFIG_NAME, AliPayProperties.class);
	}

}
