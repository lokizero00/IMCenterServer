package com.loki.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.gson.Gson;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.IntentionJournalDao;
import com.loki.server.dao.IntentionRechargeAliDao;
import com.loki.server.dao.IntentionRechargeWexinDao;
import com.loki.server.dto.AliNotifyDTO;
import com.loki.server.dto.WxpayCallbackDTO;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionJournal;
import com.loki.server.entity.IntentionRechargeAli;
import com.loki.server.entity.IntentionRechargeWexin;
import com.loki.server.service.PayCallBackService;
import com.loki.server.utils.AliPayProperties;
import com.loki.server.utils.BillConst;
import com.loki.server.utils.PayConst;

@Service
public class PayCallBackServiceImpl implements PayCallBackService {
	protected Logger logger = Logger.getLogger(PayCallBackServiceImpl.class);
	
	@Resource
	IntentionJournalDao intentionJournalDao;
	@Resource
	IntentionRechargeAliDao intentionRechargeAliDao;
	@Resource
	IntentionRechargeWexinDao intentionRechargeWexinDao;
	@Resource
	IntentionDao intentionDao;
	@Autowired
    private WxPayService wxPayService;

	/**
	 * 支付宝支付回调
	 * 
	 * @param payNotifyInfo
	 * @return
	 */
	@Transactional
	@Override
	public String alipayCallback(String payNotifyInfo) {
		String result = "fail";
		try {
			Gson gson = new Gson();
			HashMap map = gson.fromJson(payNotifyInfo, HashMap.class);
			result = this.alipayCallback(map);
		} catch (Exception e) {
			logger.error("解析支付宝支付回调数据出错: " + payNotifyInfo, e);
		}
		return result;
	}

	/**
	 * 微信支付回调处理
	 */
	@Transactional
	@Override
	public WxpayCallbackDTO wxpayCallback(String xmlData) {
		WxpayCallbackDTO wxpayCallbackDTO = new WxpayCallbackDTO();
		wxpayCallbackDTO.setReturn_code(WxPayConstants.ResultCode.FAIL);
		try {
			WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
			wxpayCallbackDTO = wxpayCallback(notifyResult);
		} catch (WxPayException e) {
			logger.error("解析微信支付回调数据出错:" + xmlData, e);
		} catch (Exception e) {
			logger.error("处理微信支付回调信息出错：" + xmlData, e);
		}
		return wxpayCallbackDTO;
	}

	/**
	 * 微信支付回调处理
	 */
	@Transactional
	public WxpayCallbackDTO wxpayCallback(WxPayOrderNotifyResult notifyResult) throws RuntimeException {
		WxpayCallbackDTO wxpayCallbackDTO = new WxpayCallbackDTO();
		// 订单号
		String outTradeNo = notifyResult.getOutTradeNo();
		// 支付状态已经交易状态
		boolean tradeStatus = notifyResult.getReturnCode().equals(WxPayConstants.BillType.SUCCESS)
				&& notifyResult.getReturnCode().equals(WxPayConstants.BillType.SUCCESS);

		// 1.校验支付记录是否存在
		IntentionJournal intentionJournal = intentionJournalDao.findByTradeNo(outTradeNo, null, null);
		if (intentionJournal == null) {
			wxpayCallbackDTO.setReturn_code(WxPayConstants.ResultCode.FAIL);
			wxpayCallbackDTO.setReturn_msg("未找到该支付记录");
			return wxpayCallbackDTO;
		}

		// 2.校验是否重复回调(若重复回调，则返回SUCCESS，避免微信反复回调)
		IntentionRechargeWexin arw = intentionRechargeWexinDao.findByOutTradeNo(outTradeNo);
		if (arw != null) {
			wxpayCallbackDTO.setReturn_code(WxPayConstants.ResultCode.SUCCESS);
			wxpayCallbackDTO.setReturn_msg("OK");
			return wxpayCallbackDTO;
		}

		// 3.校验金额是否一致
		BigDecimal amount = intentionJournal.getAmount();
		int amountCents = amount.multiply(new BigDecimal("100")).intValue();
		Integer cashFee = notifyResult.getCashFee();
		if (amountCents != cashFee) {
			wxpayCallbackDTO.setReturn_code(WxPayConstants.ResultCode.FAIL);
			wxpayCallbackDTO.setReturn_msg("回调金额错误");
			return wxpayCallbackDTO;
		}

		Intention intention = intentionDao.findById(intentionJournal.getIntentionId());
		if (intentionJournal.getType().equals(BillConst.JournalType.RECHARGE.getKey())) { // 充值
			if (tradeStatus) {
				intention.setAvailable(intention.getAvailable().add(amount));
				intention.setTotal(intention.getTotal().add(amount));
				// 5.修改账户信息
				intentionDao.update(intention);
			}
		} 

		// 6.修改充值表
		if (tradeStatus) {
			intentionJournal.setState(BillConst.JournalState.SUCCESSFULTRADE.getKey());
		} else {
			intentionJournal.setState(BillConst.JournalState.TRANSACTIONFAILURE.getKey());
		}
		intentionJournal.setThirdTransNo(notifyResult.getTransactionId());

		intentionJournal.setThirdReceiptTime(new Timestamp(System.currentTimeMillis()));
		BigDecimal cashFeeBd = new BigDecimal(cashFee);
		intentionJournal.setThirdReceiptAmount(cashFeeBd.divide(new BigDecimal("100")));
		intentionJournalDao.update(intentionJournal);

		// 保存微信支付流水
		saveWxCallBackInfo(intention, intentionJournal, cashFee, notifyResult);
		wxpayCallbackDTO.setReturn_code(WxPayConstants.ResultCode.SUCCESS);
		wxpayCallbackDTO.setReturn_msg("OK");

		return wxpayCallbackDTO;
	}

	/**
	 * 支付宝支付回调处理
	 * 
	 * @param map
	 * @return
	 */
	@Transactional
	private String alipayCallback(Map map)
			throws AlipayApiException, InvocationTargetException, IllegalAccessException, RuntimeException {
		String result = "fail";
		AliNotifyDTO aliNotifyDTO = new AliNotifyDTO();
		BeanUtils.populate(aliNotifyDTO, map);

		// 订单号
		String outTradeNo = aliNotifyDTO.getOut_trade_no();
		// 交易成功返回true 否则false
		boolean tradeStatus = aliNotifyDTO.getTrade_status().equals("TRADE_SUCCESS");

		// 1.校验支付记录是否存在
		IntentionJournal intentionJournal = intentionJournalDao.findByTradeNo(outTradeNo, null, null);
		if (intentionJournal == null) {
			logger.info("支付记录不存在");
			return result;
		}

		// 校验签名
		// AliPayProperties aliPayProperties =
		// SpringUtils.getBean(PayConst.ALI_CONFIG_NAME, AliPayProperties.class);
		boolean signStatus = AlipaySignature.rsaCheckV1(map, AliPayProperties.getInstance().getAliPayPublicKey(),
				PayConst.CHARSET, PayConst.SIGN_TYPE);
		if (!signStatus) {
			return result;
		}

		// 2.校验是否重复回调(若重复回调，则返回SUCCESS，避免支付宝反复回调)
		IntentionRechargeAli ara = intentionRechargeAliDao.findByOutTradeNo(outTradeNo);
		if (ara != null) {
			result = "success";
			logger.info("该支付已经回调");
			return result;
		}

		// 3.校验金额是否一致
		BigDecimal amount = intentionJournal.getAmount();
		BigDecimal totalAmount = new BigDecimal(aliNotifyDTO.getTotal_amount());
		if (amount.compareTo(totalAmount) != 0) {
			logger.info("回调金额不一致");
			return result;
		}

		Intention intention = intentionDao.findById(intentionJournal.getIntentionId());
		String buyerLogonId = "0";
		if (aliNotifyDTO.getBuyer_logon_id() != null) {
			buyerLogonId = aliNotifyDTO.getBuyer_logon_id();
		}

		if (intentionJournal.getType().equals(BillConst.JournalType.RECHARGE.getKey())) {// 02充值
			if (tradeStatus) {
				intention.setAvailable(intention.getAvailable().add(amount));
				intention.setTotal(intention.getTotal().add(amount));
				// 5.修改账户信息
				intentionDao.update(intention);
			}
		}
		// 交易成功
		if (tradeStatus) {
			// 6.修改充值表
			intentionJournal.setState(BillConst.JournalState.SUCCESSFULTRADE.getKey());
		} else {
			// 交易失败
			intentionJournal.setState(BillConst.JournalState.TRANSACTIONFAILURE.getKey());
		}
		intentionJournal.setThirdTransNo(aliNotifyDTO.getTrade_no());
		intentionJournal.setThirdReceiptTime(new Timestamp(System.currentTimeMillis()));
		intentionJournal.setThirdReceiptAmount(totalAmount);
		intentionJournalDao.update(intentionJournal);

		// 保存支付宝回调数据
		saveAliCallBackInfo(totalAmount, intentionJournal.getId(), intention.getUserId(), aliNotifyDTO);
		result = "success";
		return result;
	}
	
	/**
     * 保存微信流水信息
     * @param account
     * @param acctJournal
     * @param cashFee
     * @param notifyResult
     */
    @Transactional
    protected void saveWxCallBackInfo(Intention account,IntentionJournal acctJournal,Integer cashFee,WxPayOrderNotifyResult notifyResult){
        // 7.记录回调流水
        IntentionRechargeWexin intentionRechargeWexin = new IntentionRechargeWexin();
        intentionRechargeWexin.setJournalId(acctJournal.getId());
        intentionRechargeWexin.setUserId(account.getUserId());
        intentionRechargeWexin.setReturnCode(notifyResult.getReturnCode());
        intentionRechargeWexin.setReturnMsg(notifyResult.getReturnMsg());
        intentionRechargeWexin.setAppid(notifyResult.getAppid());
        intentionRechargeWexin.setMchId(notifyResult.getMchId());
        intentionRechargeWexin.setNonceStr(notifyResult.getNonceStr());
        intentionRechargeWexin.setSign(notifyResult.getSign());
        intentionRechargeWexin.setResultCode(notifyResult.getResultCode());
        intentionRechargeWexin.setOpenid(notifyResult.getOpenid());
        intentionRechargeWexin.setTradeType(notifyResult.getTradeType());
        intentionRechargeWexin.setBankType(notifyResult.getBankType());
        intentionRechargeWexin.setTotalFee(new BigDecimal(notifyResult.getTotalFee()));
        intentionRechargeWexin.setCashFee(new BigDecimal(cashFee));
        intentionRechargeWexin.setTransactionId(notifyResult.getTransactionId());
        intentionRechargeWexin.setOutTradeNo(notifyResult.getOutTradeNo());
        intentionRechargeWexin.setTimeEnd(notifyResult.getTimeEnd());
        intentionRechargeWexin.setCreateTime(new Timestamp(System.currentTimeMillis()));
        intentionRechargeWexinDao.insert(intentionRechargeWexin);
    }

	/**
	 * 保存支付宝流水信息
	 * 
	 * @param totalAmount
	 * @param journalId
	 * @param userId
	 * @param aliNotifyDTO
	 */
	@Transactional
	protected void saveAliCallBackInfo(BigDecimal totalAmount, Integer journalId, Integer userId,
			AliNotifyDTO aliNotifyDTO) {
		// 7.记录回调流水
		BigDecimal receiptAmount = totalAmount;
		BigDecimal buyerPayAmount = totalAmount;
		IntentionRechargeAli intentionRechargeAli = new IntentionRechargeAli();
		intentionRechargeAli.setJournalId(journalId);
		intentionRechargeAli.setUserId(userId);
		intentionRechargeAli.setRtvCode(aliNotifyDTO.getNotify_type());
		intentionRechargeAli.setRtvMsg(aliNotifyDTO.getNotify_id());
		intentionRechargeAli.setSubCode(aliNotifyDTO.getTrade_status());
		intentionRechargeAli.setSubMsg("0");
		intentionRechargeAli.setSign(aliNotifyDTO.getSign() == null ? "" : aliNotifyDTO.getSign());
		intentionRechargeAli.setTradeNo(aliNotifyDTO.getTrade_no());
		intentionRechargeAli.setOutTradeNo(aliNotifyDTO.getOut_trade_no());
		intentionRechargeAli
				.setBuyerLogonId((aliNotifyDTO.getBuyer_logon_id() == null ? "0" : aliNotifyDTO.getBuyer_logon_id()));
		intentionRechargeAli.setTotalAmount(totalAmount);
		intentionRechargeAli.setReceiptAmount(receiptAmount);
		intentionRechargeAli.setBuyerPayAmount(buyerPayAmount);
		intentionRechargeAli.setGmtPayment(new Timestamp(System.currentTimeMillis()));
		intentionRechargeAli.setBuyerUserId("0");
		intentionRechargeAli.setFundBillList(aliNotifyDTO.getFund_bill_list());
		intentionRechargeAli.setVoucherDetailList(aliNotifyDTO.getVoucher_detail_list());
		intentionRechargeAli.setCreateTime(new Timestamp(System.currentTimeMillis()));
		intentionRechargeAliDao.insert(intentionRechargeAli);
	}

}
