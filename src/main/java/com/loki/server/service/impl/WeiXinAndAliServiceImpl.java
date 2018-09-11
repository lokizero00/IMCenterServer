package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.IntentionJournalDao;
import com.loki.server.dao.IntentionRefundDao;
import com.loki.server.dao.SettingDao;
import com.loki.server.dao.UserExtensionDao;
import com.loki.server.dto.AliRechargeDTO;
import com.loki.server.dto.RechargeDTO;
import com.loki.server.dto.Transfer;
import com.loki.server.dto.WxRechargeDTO;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionJournal;
import com.loki.server.entity.IntentionRefund;
import com.loki.server.entity.UserExtension;
import com.loki.server.service.WeiXinAndAliService;
import com.loki.server.utils.AliPayProperties;
import com.loki.server.utils.BillConst;
import com.loki.server.utils.ForwardRequest;
import com.loki.server.utils.OrderNoGenerator;
import com.loki.server.utils.PayConst;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.utils.SpringUtils;
import com.loki.server.utils.WxPayProperties;
import com.loki.server.utils.XmlUtil;
import com.loki.server.vo.ServiceResult;

@Service
public class WeiXinAndAliServiceImpl extends BaseService implements WeiXinAndAliService {

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

	@Resource
	private UserExtensionDao userExtensionDao;

	@Resource
	private IntentionRefundDao intentionRefundDao;

	@Resource
	private IntentionDao intentionDao;

	@Resource
	IntentionJournalDao intentionJournalDao;
	
	@Resource
	SettingDao settingDao;

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
			AliPayProperties baseAliProperties = AliPayProperties.getInstance(); // 获取押金对象
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

	// /**
	// * 支付宝-获取账户信息
	// */
	// private AliPayProperties aliUnifiedOrder() {
	// return SpringUtils.getBean(PayConst.ALI_CONFIG_NAME, AliPayProperties.class);
	// }

	/**
	 * 获取支付宝登录infoStr
	 * 
	 * @return
	 */
	@Override
	public ServiceResult<String> awakenAliAuthInfoStr() {
		AliPayProperties baseAliProperties = new AliPayProperties();
		ServiceResult<String> resultDO = new ServiceResult<>();
		String targetId = OrderNoGenerator.getPayOrderNo(2);
		String infoStr = "apiname=com.alipay.account.auth" + "&app_id=" + baseAliProperties.getAppId() + "&app_name=mc"
				+ "&auth_type=AUTHACCOUNT" + "&biz_type=openservice" + "&method=alipay.open.auth.sdk.code.get" + "&pid="
				+ baseAliProperties.getPid() + "&product_id=APP_FAST_LOGIN" + "&scope=kuaijie" + "&sign_type=RSA2"
				+ "&target_id=" + targetId + "&sign_type=RSA2";
		try {
			String sign = AlipaySignature.rsaSign(infoStr, baseAliProperties.getAppPrivateKey(), "UTF-8", "RSA2");
			infoStr = infoStr + "&sign=" + URLEncoder.encode(sign, "UTF-8");
			resultDO.setResultObj(infoStr);
			resultDO.setResultCode(ResultCodeEnums.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			resultDO.setResultCode(ResultCodeEnums.ALIPAY_AUTH_ERROR);
		}

		return resultDO;
	}

	/**
	 * 支付宝验证 --使用auth_code获取access_token与user_id
	 * 
	 * @author XiongXiaobo
	 */
	@Override
	public ServiceResult<Void> getAlipayAccount(String authCode, int userId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			AliPayProperties baseAliProperties = new AliPayProperties();
			// 校验参数
			if (!StringUtils.isEmpty(authCode)) {
				try {
					AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
							baseAliProperties.getAppId(), baseAliProperties.getAppPrivateKey(), "json", "UTF-8",
							baseAliProperties.getAliPayPublicKey(), "RSA2");
					// 使用auth_code获取access_token与user_id
					AlipaySystemOauthTokenRequest requests = new AlipaySystemOauthTokenRequest();
					requests.setCode(authCode);
					requests.setGrantType("authorization_code");
					AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(requests);
					if (oauthTokenResponse.isSuccess()) {
						AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
						AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser,
								oauthTokenResponse.getAccessToken());
						if (userinfoShareResponse != null) {
							String aliUserId = userinfoShareResponse.getUserId();
							// 绑定支付宝数据
							UserExtension userExtension = userExtensionDao.findByUserId(userId);
							if (null != userExtension) {
								userExtension.setAliAccount(aliUserId);
								userExtension.setAliBind(1);
								userExtensionDao.update(userExtension);
							} else {
								userExtension = new UserExtension();
								userExtension.setUserId(userId);
								userExtension.setAliAccount(aliUserId);
								userExtension.setAliBind(1);
								userExtensionDao.insert(userExtension);
							}
							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						}
					} else {
						logger.error("支付宝获取用户信息失败，原因：" + oauthTokenResponse.getMsg());
						returnValue.setResultCode(ResultCodeEnums.ALIPAY_BIND_ACCOUNT_ERROR);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue.setResultCode(ResultCodeEnums.ALIPAY_BIND_ACCOUNT_ERROR);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}

		return returnValue;
	}

	/**
	 * 识别得到用户id必须的一个值
	 * 
	 * @param code
	 * @return
	 */
	@Override
	// 根据用户的code得到用户OpenId
	public ServiceResult<Void> getWxOpenid(String code, int userId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			WeixinUserInfoServiceImpl weixinGetCode = new WeixinUserInfoServiceImpl();
			Map<String, Object> result = weixinGetCode.oauth2GetOpenid(code);
			if (result != null) {
				String openId = (String) result.get("Openid");// 得到用户id
				// 绑定支付宝数据
				UserExtension userExtension = userExtensionDao.findByUserId(userId);
				if (null != userExtension) {
					userExtension.setWechatAccount(openId);
					userExtension.setWechatBind(1);
					userExtensionDao.update(userExtension);
				} else {
					userExtension = new UserExtension();
					userExtension.setUserId(userId);
					userExtension.setWechatAccount(openId);
					userExtension.setWechatBind(1);
					userExtensionDao.insert(userExtension);
				}
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			} else {
				returnValue.setResultCode(ResultCodeEnums.WX_AUTH_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	/**
	 * 退款- 根据orderType字段退款
	 * 
	 * @param acctJournal
	 * @param acctRefundBill
	 * @return
	 * @throws BizException
	 */
	@Override
	public void refund(IntentionRefund intentionRefund, int adminPayerId) throws ServiceException {
		switch (intentionRefund.getRefundChannel()) {
		case 0:// 微信
			// 微信转账
			Map<String, String> map = WxTransfer(intentionRefund, adminPayerId);
			if (!"SUCCESS".equals(map.get("result_code"))) {
				// throw new ServiceException(ResultCodeEnums.WX_REFUND_ERROR);
			}
			break;
		case 1:// 支付宝
				// 支付宝转账
			AlipayFundTransToaccountTransferResponse response = aliTransfer(intentionRefund, adminPayerId);
			if (!response.getCode().equals("10000")) {
				// throw new ServiceException(ResultCodeEnums.ALIPAY_REFUND_ERROR);
			}
			break;
		default:
			throw new ServiceException(ResultCodeEnums.INTENTION_REFUND_CHANNEL_ERROR);
		}
	}

	/**
	 * 支付宝转账
	 * 
	 * @return
	 */
	private AlipayFundTransToaccountTransferResponse aliTransfer(IntentionRefund intentionRefund, int adminPayerId) {
		AliPayProperties depProperties = AliPayProperties.getInstance();
		AlipayClient alipayClient = new DefaultAlipayClient(depProperties.getGatewayUrl(), depProperties.getAppId(),
				depProperties.getAppPrivateKey(), PayConst.FORMAT, PayConst.CHARSET, depProperties.getAliPayPublicKey(),
				PayConst.SIGN_TYPE);
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		AlipayFundTransToaccountTransferResponse response = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("out_biz_no", intentionRefund.getOutRequestNo());
		requestMap.put("payee_type", "ALIPAY_USERID");
		requestMap.put("payee_account", intentionRefund.getRefundAccount());
		
		//扣除手续费，0.006
		String aliCashServiceFee=settingDao.findByName("aliCashServiceFee");
		if(aliCashServiceFee==null || aliCashServiceFee=="") {
			aliCashServiceFee="0.006";
		}
		
		BigDecimal money=(intentionRefund.getAmount().multiply(BigDecimal.ONE.subtract(new BigDecimal(aliCashServiceFee)))).setScale(2, BigDecimal.ROUND_DOWN);
		requestMap.put("amount", money + "");
		requestMap.put("remark", "互联制造意向金提现");
		try {
			request.setBizContent(mapper.writeValueAsString(requestMap));
			response = alipayClient.execute(request);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		// 则为退款成功
		if (response.getCode().equals("10000")) {
			returnDepositHandle(true, intentionRefund, "提现成功，金额："+money, adminPayerId);
		} else {
			returnDepositHandle(false, intentionRefund, response.getSubMsg(), adminPayerId);
		}
		// 则转账成功
		return response;
	}

	/**
	 * 微信转账
	 * 
	 * @return
	 */
	private Map<String, String> WxTransfer(IntentionRefund intentionRefund, int adminPayerId) {
		String TRANSFER_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
		// 微信转账
		Transfer transfer = new Transfer();
		transfer.setMch_appid(WxPayProperties.getInstance().getAppId());
		transfer.setMchid(WxPayProperties.getInstance().getMchId());
		// 拿到openid
		transfer.setOpenid(intentionRefund.getRefundAccount());
		// 将单位元转换为分
		BigDecimal money = null;
		money = intentionRefund.getAmount();
		//扣除手续费，0.006
		String wxCashServiceFee=settingDao.findByName("wxCashServiceFee");
		if(wxCashServiceFee==null || wxCashServiceFee=="") {
			wxCashServiceFee="0.006";
		}
		money=(money.multiply(BigDecimal.ONE.subtract(new BigDecimal(wxCashServiceFee)))).setScale(2, BigDecimal.ROUND_DOWN);
		transfer.setAmount((money.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN)).intValue());
		// transfer.setRe_user_name(acctRefundBill.getUserName());
		transfer.setPartner_trade_no(intentionRefund.getOutRequestNo());
		String nonceStr = OrderNoGenerator.getRandomStr(12);// 随机生成一段字符串
		transfer.setNonce_str(nonceStr);
		// 拿到IP
		InetAddress address = null;// 获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String hostAddress = address.getHostAddress();// 192.168.0.121
		transfer.setSpbill_create_ip(hostAddress);

		HashMap hashMap = new HashMap();
		hashMap.put("mch_appid", transfer.getMch_appid());
		hashMap.put("mchid", transfer.getMchid());
		hashMap.put("nonce_str", transfer.getNonce_str());
		hashMap.put("partner_trade_no", transfer.getPartner_trade_no());
		hashMap.put("openid", transfer.getOpenid());
		hashMap.put("check_name", transfer.getCheck_name());
		// hashMap.put("re_user_name", transfer.getRe_user_name());
		hashMap.put("amount", transfer.getAmount() + "");
		hashMap.put("desc", transfer.getDesc());
		hashMap.put("spbill_create_ip", transfer.getSpbill_create_ip());
		// 拿到签名
		String sign = SignUtils.createSign(hashMap, WxPayConstants.SignType.MD5,
				WxPayProperties.getInstance().getMchKey(), false);
		transfer.setSign(sign);// 设置签名
		// 转化成xml格式 便于发送请求
		String requestXml = XmlUtil.objToXml(transfer, "utf-8");
		// 发送请求
		String responseDate = ForwardRequest.httpPostSsl(TRANSFER_URL, requestXml, "UTF-8",
				wxPayService.getConfig().getKeyPath(), wxPayService.getConfig().getMchId());
		Map<String, String> map = new HashMap<>();
		if (StringUtils.isEmpty(responseDate)) {
			// 转换格式
			map = XmlUtil.xmlStrToMap(responseDate);
			if (map.size() > 0) {
				if (map.get("result_code").equals("SUCCESS") && map.get("return_code").equals("SUCCESS")) {
					returnDepositHandle(true, intentionRefund, "提现成功，金额："+money, adminPayerId);
				} else {
					returnDepositHandle(false, intentionRefund, map.get("return_msg"), adminPayerId);
				}
			}
		}
		return map;
	}

	/**
	 * 退押金回调业务处理
	 * 
	 * @param refundStatus
	 *            交易状态
	 * @param intentionRefund
	 * @throws RuntimeException
	 */
	private void returnDepositHandle(Boolean refundStatus, IntentionRefund intentionRefund, String errorMsg,
			int adminPayerId) throws RuntimeException {
		if (refundStatus) {
			// 交易成功
			intentionRefund.setState(PayConst.DepositPayBackType.RETURN_SUCCESS.getIndex());
			List<Integer> userNoticeIds=new ArrayList<>();
			userNoticeIds.add(intentionRefund.getUserId());
			addNotice(4,null, "意向金提现成功", null,userNoticeIds);
		} else {
			// 交易失败
			intentionRefund.setState(PayConst.DepositPayBackType.RETURN_ERROR.getIndex());
			List<Integer> userNoticeIds=new ArrayList<>();
			userNoticeIds.add(intentionRefund.getUserId());
			addNotice(4,null, "意向金提现失败", null,userNoticeIds);
		}
		intentionRefund.setErrorMsg(errorMsg);
		intentionRefund.setAdminPayerId(adminPayerId);
		intentionRefund.setFinishTime(new Timestamp(System.currentTimeMillis()));
		intentionRefundDao.update(intentionRefund);

		// 退押金成功则更新账户
		if (refundStatus) {
			try {
				Intention intention = intentionDao.findById(intentionRefund.getIntentionId());
				// 退款成功，清楚冻结的提现金额
				intention.setFreeze(intention.getFreeze().add(intentionRefund.getAmount().negate()));
				intention.setTotal(intention.getTotal().add(intentionRefund.getAmount().negate()));
				intentionDao.update(intention);
				// 更新流水状态
				IntentionJournal intentionJournal = intentionJournalDao.findById(intentionRefund.getJournalId());
				if (intentionJournal != null) {
					intentionJournal.setState("01");
					intentionJournal.setThirdReceiptTime(new Timestamp(System.currentTimeMillis()));
					intentionJournalDao.update(intentionJournal);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Intention intention = intentionDao.findById(intentionRefund.getIntentionId());
				// 退款失败，退还冻结的提现金额
				intention.setFreeze(intention.getFreeze().add(intentionRefund.getAmount().negate()));
				intention.setAvailable(intention.getAvailable().add(intentionRefund.getAmount()));
				intentionDao.update(intention);
				// 更新流水状态
				IntentionJournal intentionJournal = intentionJournalDao.findById(intentionRefund.getJournalId());
				if (intentionJournal != null) {
					intentionJournal.setState("02");
					intentionJournal.setThirdReceiptTime(new Timestamp(System.currentTimeMillis()));
					intentionJournalDao.update(intentionJournal);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
