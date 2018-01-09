package com.tcode.common.pay.wechat.util;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.weixin4j.OAuth2User;

import net.sf.json.JSONObject;

import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.member.model.Member;
import com.tcode.common.pay.wechat.config.WechatConfig;
import com.tcode.common.pay.wechat.model.Unifiedorder;
import com.tcode.common.pay.wechat.model.WeixinOrder;
import com.tcode.core.util.JSONUtils;
import com.tcode.core.util.Utils;
import com.tcode.core.util.XmlJSONUtil;

public class WechatPay {

	/**
	 * 微信商城支付
	 * @param member
	 * @param clientIp
	 * @param mallOrder
	 * @param setting
	 * @return
	 * @throws Exception
	 */
	public static String getPayString(Member member, String clientIp, MallOrderHead mallOrder, MallSetting setting) throws Exception {
		
		String body = setting.getTitle().replaceAll("<", "(").replaceAll(">", ")") + "商品"; 
		String trade_type = "JSAPI";
		
		String nonce_str = RandCharsUtils.getRandomString(16);

		double totalfee = mallOrder.getAprice();// //单位是分，即是0.01元
		int total_fee = (int) (totalfee * 100);

		String notify_url = WechatConfig.notify_url;
		
		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		
		parameters.put("appid", setting.getAppId());
		parameters.put("mch_id", setting.getMchId());
		parameters.put("device_info", "WEB"); 
		parameters.put("body", body);
		parameters.put("nonce_str", nonce_str);
		parameters.put("out_trade_no", mallOrder.getOrderId());
		parameters.put("total_fee", total_fee);
		parameters.put("spbill_create_ip", clientIp);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		parameters.put("openid", member.getWechatNo()); 
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, setting.getAppSecret());
		
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(setting.getAppId());
		unifiedorder.setMch_id(setting.getMchId());
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setDevice_info("WEB");
		unifiedorder.setBody(body);
		unifiedorder.setOut_trade_no(mallOrder.getOrderId());
		unifiedorder.setTotal_fee(total_fee);
		unifiedorder.setSpbill_create_ip(clientIp);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type(trade_type);
		unifiedorder.setOpenid(member.getWechatNo());
		unifiedorder.setSign(sign);

		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String method = "POST";
		
		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
		
		String json = XmlJSONUtil.xmlToJSON(weixinPost);
		System.out.println("微信商城支付字符串：" + json);
		
		
		JSONObject wxJson = JSONObject.fromObject(json);
		String payStr = "";
		WeixinOrder weixin = JSONUtils.toBean(wxJson.get("xml"), WeixinOrder.class);
		if (!Utils.isEmpty(weixin)) {
			SortedMap<Object, Object> par = new TreeMap<Object, Object>();
			par.put("appId", weixin.getAppid());
			par.put("timeStamp", Long.toString(new Date().getTime())); 
			par.put("nonceStr", weixin.getNonce_str());
			par.put("package", "prepay_id=" + weixin.getPrepay_id());
			par.put("signType", "MD5"); 
			par.put("paySign", WXSignUtils.createSign("UTF-8", par, setting.getAppSecret()));
			payStr = JSONObject.fromObject(par).toString(); 
		}
		return payStr;
	}
	
	/**
	 * 微信活动支付
	 * @param member
	 * @param clientIp
	 * @param mallOrder
	 * @param setting
	 * @return
	 */
	public static String getActPayString(OAuth2User auth2User, String clientIp, MallSetting setting, String name, Double price, String orderNo) throws Exception {
		
		String body = name.replaceAll("<", "(").replaceAll(">", ")"); 
		String trade_type = "JSAPI";
		
		String nonce_str = RandCharsUtils.getRandomString(16);

		double totalfee = price;// //单位是分，即是0.01元
		int total_fee = (int) (totalfee * 100);

		String notify_url = WechatConfig.notify_url;
		
		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		
		parameters.put("appid", setting.getAppId());
		parameters.put("mch_id", setting.getMchId());
		parameters.put("device_info", "WEB"); 
		parameters.put("body", body);
		parameters.put("nonce_str", nonce_str);
		parameters.put("out_trade_no", orderNo);
		parameters.put("total_fee", total_fee);
		parameters.put("spbill_create_ip", clientIp);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		parameters.put("openid", auth2User.getOpenid()); 
		
		String sign = WXSignUtils.createSign("UTF-8", parameters, setting.getAppSecret());
		
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(setting.getAppId());
		unifiedorder.setMch_id(setting.getMchId());
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setDevice_info("WEB");
		unifiedorder.setBody(body);
		unifiedorder.setOut_trade_no(orderNo);
		unifiedorder.setTotal_fee(total_fee);
		unifiedorder.setSpbill_create_ip(clientIp);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type(trade_type);
		unifiedorder.setOpenid(auth2User.getOpenid());
		unifiedorder.setSign(sign);

		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String method = "POST";
		
		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
		
		String json = XmlJSONUtil.xmlToJSON(weixinPost);
		System.out.println("微信活动支付字符串：" + json);
		
		JSONObject wxJson = JSONObject.fromObject(json);
		String payStr = "";
		WeixinOrder weixin = JSONUtils.toBean(wxJson.get("xml"), WeixinOrder.class);
		if (!Utils.isEmpty(weixin)) {
			SortedMap<Object, Object> par = new TreeMap<Object, Object>();
			par.put("appId", weixin.getAppid());
			par.put("timeStamp", Long.toString(new Date().getTime())); 
			par.put("nonceStr", weixin.getNonce_str());
			par.put("package", "prepay_id=" + weixin.getPrepay_id());
			par.put("signType", "MD5"); 
			par.put("paySign", WXSignUtils.createSign("UTF-8", par, setting.getAppSecret()));
			payStr = JSONObject.fromObject(par).toString(); 
		}
		return payStr;
	}
}
