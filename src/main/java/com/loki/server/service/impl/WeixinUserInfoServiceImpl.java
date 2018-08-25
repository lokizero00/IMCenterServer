package com.loki.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.loki.server.dto.WeixinUserInfo;
import com.loki.server.utils.HttpUtil;
import com.loki.server.utils.WxPayProperties;

import net.sf.json.JSONObject;

public class WeixinUserInfoServiceImpl {
	/**
	 * 得到微信用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	
	protected Logger logger = Logger.getLogger(WeiXinAndAliServiceImpl.class);
	
	public WeixinUserInfo getUserInfo(String accessToken, String openId) {

		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe())
					logger.error("用户{}已取消关注,"+weixinUserInfo.getOpenId());
				else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					logger.error("获取用户信息失败 errmsg:"+errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

	/**
	 * 
	 * @param code
	 *            识别得到用户id必须的一个值 得到网页授权凭证和用户id
	 * @return
	 */
	public Map<String, Object> oauth2GetOpenid(String code) {
		WxPayProperties wxPayProperties=new WxPayProperties();
		String appid = wxPayProperties.getAppId();// 自己的配置appid
		String appsecret = wxPayProperties.getAppSecret();// 自己的配置APPSECRET;
		String requestUrl = GetPageAccessTokenUrl.replace("APPID", appid).replace("SECRET", appsecret)
				.replace("CODE", code);
		HttpClient client = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(requestUrl);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = client.execute(httpget, responseHandler);
			JSONObject OpenidJSONO = JSONObject.fromObject(response);

			// OpenidJSONO可以得到的内容：access_token expires_in refresh_token openid scope

			String Openid = String.valueOf(OpenidJSONO.get("openid"));
			String AccessToken = String.valueOf(OpenidJSONO.get("access_token"));
			String Scope = String.valueOf(OpenidJSONO.get("scope"));// 用户保存的作用域
			String refresh_token = String.valueOf(OpenidJSONO.get("refresh_token"));

			result.put("Openid", Openid);
			result.put("AccessToken", AccessToken);
			result.put("scope", Scope);
			result.put("refresh_token", refresh_token);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
		return result;
	}

	// 网页授权获取code
	public final static String GetPageCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
	// 网页授权接口
	public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 网页授权得到用户基本信息接口
	public final static String GetPageUsersUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
}
