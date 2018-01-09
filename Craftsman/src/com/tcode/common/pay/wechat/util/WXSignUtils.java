package com.tcode.common.pay.wechat.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 微信支付签名
 * @author iYjrg_xiebin
 * @date 2015年11月25日下午4:47:07
 */
public class WXSignUtils {

	/**
	 * 微信支付签名算法sign
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters, String appSecret){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + appSecret);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		
		return sign;
	}


	public static void main(String[] args) {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("is_subscribe", "N");
		parameters.put("appid", "wxada9bad2d1e3b761");
		parameters.put("fee_type", "CNY");
		parameters.put("nonce_str", "chUrR35phcgfdY5Y");
		parameters.put("out_trade_no", "20170120155925727");
		parameters.put("transaction_id", "4008922001201701206968065186");
		parameters.put("trade_type", "NATIVE");
		parameters.put("result_code", "SUCCESS");
		parameters.put("mch_id", "1435605802");
		parameters.put("total_fee", "1");
		parameters.put("attach", "11");
		parameters.put("time_end", "20170120155950");
		parameters.put("openid", "o7ABZwknI8Q8pXvTjxxT7R4TC8lo");
		parameters.put("bank_type", "CFT");
		parameters.put("return_code", "SUCCESS");
		parameters.put("cash_fee", "1");

//		String sign = WXSignUtils.createSign("UTF-8", parameters);
//		System.out.println(sign);
//		0BA214049BBCB9BECA4D581153DCE162
	}
}
