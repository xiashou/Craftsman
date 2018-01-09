package com.tcode.open.wechat.action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.Weixin;
import org.weixin4j.aes.SHA1;
import org.weixin4j.pay.JsApiTicket;

import com.tcode.business.member.model.Member;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.open.wechat.bean.jssdk.WXConfig;

/**
 * 微信网页接口
 * @author supeng
 *
 */
@Scope("prototype")
@Component("WechatJSSDKAction")
public class WechatJSSDKAction  extends BaseAction {
	
	private WXConfig wxConfig;
	
	public String getJSSDKParams() {
		HttpServletRequest request =  getRequest();
		Member member = request.getSession().getAttribute("member") == null ? new Member() : (Member) request.getSession().getAttribute("member");
		String appId = Utils.isEmpty(member.getAppId()) ? "" : member.getAppId();
		try {
			if(Utils.isEmpty(appId) && !Utils.isEmpty(request.getParameter("sid"))){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(request.getParameter("sid").toString());
				if(!Utils.isEmpty(wechatAuthorizerParams)) {
					appId = wechatAuthorizerParams.getAuthorizerAppId();
				}
			}
			JsApiTicket jsApiTicket = new Weixin().getJsApi_Ticket(appId);        
			if(jsApiTicket != null) {
				String jsapi_ticket = jsApiTicket.getTicket();
				String tempTime = String.valueOf(System.currentTimeMillis());
				int length = tempTime.length();
				String timestamp = tempTime.substring(0,length-3);
				String noncestr = UUID.randomUUID().toString();
				noncestr = "asdasfasfsaf";
//				String url = request.getScheme() //当前链接使用的协议
//					    +"://" + request.getServerName()//服务器地址 
//					    + ":" + request.getServerPort() //端口号 
//					    + request.getContextPath() //应用名称，如果应用名称为
//					    + request.getServletPath() //请求的相对url 
//					    + "?" + request.getQueryString(); //请求参数
				String url = wxConfig.getUrl();
				Map<String, String> paraMap = new HashMap<String, String>();
				paraMap.put("jsapi_ticket", jsapi_ticket);
				paraMap.put("noncestr", noncestr);
				paraMap.put("timestamp", timestamp);
				paraMap.put("url", url);
				String result = Utils.formatUrlMap(paraMap, false, false);
				String signature = SHA1.getSHA1(result);
				wxConfig = new WXConfig();
				wxConfig.setAppId(appId);
				wxConfig.setNonceStr(noncestr);
				wxConfig.setTimestamp(timestamp);
				wxConfig.setSignature(signature);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public WXConfig getWxConfig() {
		return wxConfig;
	}

	public void setWxConfig(WXConfig wxConfig) {
		this.wxConfig = wxConfig;
	}
	
}
