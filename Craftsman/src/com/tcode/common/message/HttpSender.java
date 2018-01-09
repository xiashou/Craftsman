package com.tcode.common.message;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.tcode.core.util.Utils;

/**
 * @param url 应用地址，类似于http://ip:port/msg/
 * @param account 账号
 * @param pswd 密码
 * @param mobile 手机号码，多个号码使用","分割
 * @param msg 短信内容
 * @param needstatus 是否需要状态报告，需要true，不需要false
 * @return 返回值定义参见HTTP协议文档
 * @throws Exception
 */
public class HttpSender {

	private static Logger log = Logger.getLogger("SLog");
//	private final static String url = "http://120.24.167.205/msg/HttpSendSM";// 应用地址
//	private final static String account = "gzqxkj_szzngj";// 账号
//	private final static String pswd = "SZzngj03";// 密码
//	private final static boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
	// private final static String extno = null;// 扩展码
	
	private final static String username = "zhineng";// 设置帐号
	private final static String username2 = "yingxiao";// 设置帐号
	private final static String passwd = "123456";// 设置密码

	/**
	 * 短信发送
	 * 
	 * @param mobile 手机号码，多个号码使用","分割
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public static int batchSend(String mobile, String msg) throws Exception {
		return sendMsg(username2, mobile, msg, "true", "", "");
	}
	
	public static int batchSend2(String mobile, String msg) throws Exception {
		return sendMsg(username, mobile, msg, "true", "", "");
	}
	
	
	public static int sendMsg(String userName, String phone, String msg, String needstatus, String port, String sendtime) throws Exception {
		int sendStatus = 1;// 发送状态 0-成功，1-失败
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://www.qybor.com:8500/shortMessage");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair[] data = { new NameValuePair("username", userName),
				new NameValuePair("passwd", passwd),
				new NameValuePair("phone", phone),
				new NameValuePair("msg", msg),
				new NameValuePair("needstatus", needstatus),
				new NameValuePair("port", port),
				new NameValuePair("sendtime", sendtime) };
		post.setRequestBody(data);
		client.executeMethod(post);
		String result = new String(post.getResponseBodyAsString().getBytes());
		System.out.println(result);
		post.releaseConnection();
		ResultObj group = JSON.parseObject(result, ResultObj.class);
		if(!Utils.isEmpty(group)){
			if(!Utils.isEmpty(group.getRespcode()) && "0".equals(group.getRespcode()))
				sendStatus = 0;
			else
				log.warn("短信发送失败：" + "Batchno: " + group.getBatchno() + "|" + group.getRespdesc());
		} else
			log.warn("短信发送失败：" + "HTTP ERROR Status: " + post.getStatusCode() + "|" + post.getStatusText());
		return sendStatus;
	}
	
	/**
	 * 发送结果bean
	 * @author Administrator
	 * @throws Exception 
	 *
	 */
	public static void main(String[] args) throws Exception {
//		String result = "{\"batchno\":\"\",\"respcode\":\"109\",\"respdesc\":\"关键字匹配失败:群发短信\"}";
//		ResultObj group = JSON.parseObject(result, ResultObj.class);
//		System.out.println(group.getRespdesc());
		HttpSender.sendMsg("zhineng", "18503087176", "【智能工匠】尊敬的林先生您好！您本次进行[蜡水洗车]等消费共30元，获得3积分，当前余额：150元。详情关注本店微信公众号：七元素汽车服务，领取礼包，感谢您对本店的支持，服务电话0755-66601026。", "true", "", "");
	}

}