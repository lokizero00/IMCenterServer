package com.loki.server.utils;

import java.util.Date;
import java.util.Random;

public class OrderNoGenerator {
	/**
	 * 生成充值订单号
	 * @param orderType
	 * @return
	 */
	public static String getPayOrderNo(Integer orderType) {

		String orderNo = null;
		Date currentTime = new Date();
		String timeInfo = DateTool.dateToStr(currentTime,DateTool.DATE_TIME_SORT_SSS);
		String randomNum = getFixLenthString(3);

		if(orderType == BillConst.BillOrder.RECHARGE.getKey()){
			orderNo="R"+timeInfo+randomNum;
			return orderNo;
		} else if(orderType == BillConst.BillOrder.FREEZE.getKey()){
			orderNo="F"+timeInfo+randomNum;
			return orderNo;
		} else if(orderType == BillConst.BillOrder.UNFREEZE.getKey()){
			orderNo="UF"+timeInfo+randomNum;
			return orderNo;
		} 
		return orderNo;
	}

//	/**
//	 * 生成缴纳押金、充值,购买套餐订单号
//	 * @param orderType
//	 * @return
//	 */
//	public static String getOrderNo(Integer orderType) {
//
//		String orderNo = null;
//		Date currentTime = new Date();
//		String timeInfo = DateTool.dateToStr(currentTime,DateTool.DATE_TIME_SORT_SSS);
//		String randomNum = getFixLenthString(3);
//
//		if(orderType == OrderConst.UserOrderType.BATTERY_RENT.getKey()){
//			orderNo="BR"+timeInfo+randomNum;
//			return orderNo;
//		} else if(orderType == OrderConst.UserOrderType.BATTERY_CHANGE.getKey()) {
//			orderNo = "BC" + timeInfo + randomNum;
//			return orderNo;
//		}else if(orderType == OrderConst.UserOrderType.RETURN_DEPOSIT.getKey()) {
//			orderNo = "RD" + timeInfo + randomNum;
//			return orderNo;
//		}else if(orderType == OrderConst.UserOrderType.VEHICLE_RENEW.getKey()){
//			orderNo = "VRR" + timeInfo + randomNum;
//			return orderNo;
//		}else if(orderType == OrderConst.UserOrderType.VEHICLE_RENT.getKey()){
//			orderNo = "VR" + timeInfo + randomNum;
//			return orderNo;
//		}
//		return orderNo;
//	}
//
//	//部分退款生成唯一out_request_no
//	public static String getOuRequestNo() {
//		String orderNo = null;
//		Date currentTime = new Date();
//		String timeInfo = DateTool.dateToStr(currentTime,DateTool.DATE_TIME_YMD_NOSYMBOL);
//		String randomNum = getFixLenthString(3);
//		orderNo="BR"+timeInfo+randomNum;
//		return orderNo;
//	}
//
//
	/**
     * 返回长度为【strLength】的随机数，在前面补0
	 * @param strLength
     */
	private static String getFixLenthString(int strLength) {

		Random rm = new Random();

		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);

		// 返回固定的长度的随机数
		return fixLenthString.substring(1, strLength + 1);
	}
}
