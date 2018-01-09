package com.tcode.open.wechat.action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.Configuration;
import org.weixin4j.OAuth2;
import org.weixin4j.OAuth2User;
import org.weixin4j.WeixinException;
import org.weixin4j.http.OAuth2Token;

import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.model.WechatComponentParams;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.business.wechat.sys.util.WechatComponentParamsUtil;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

import net.sf.json.JSONObject;

/**
 * 网页鉴权，向business_redirect_uri返回OAuth2User对象json字符串
 * 调用示例
 * http://1e59r75844.iask.in/open/wechat/sys/authorizeOauth2initOAuth2User.atc?sid=123&business_redirect_uri=http://www.qq.com&scope=snsapi_userinfo
 * @author supeng
 *
 */
@Scope("prototype")
@Component("AuthorizeOauth2Action")
public class AuthorizeOauth2Action extends BaseAction {
	
	
	private String sid;//自定义授权码，对应唯一授权方appId，每个页面必须带有此参数
	private String business_redirect_uri;//业务回调url
	private String scope;//应用授权作用域 snsapi_base和snsapi_userinfo
	private String state;
	private String code;
	private String appId;
	
	public String initOAuth2User() throws Exception {
		
		initDataPramas();
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + Utils.convertServerName(request.getServerName())
				+ path + "/";
		
		if(!Utils.isEmpty(business_redirect_uri) && !Utils.isEmpty(scope)) {
			
			business_redirect_uri =  URLEncoder.encode(business_redirect_uri, "UTF-8");
			
			StringBuffer redirect_uri_buffer = new StringBuffer();// 鉴权后回调URL urlencode对链接进行处理
			redirect_uri_buffer.append(basePath).append("open/wechat/sys/authorizeOauth2initUserInfo.atc")
			.append("?business_redirect_uri=").append(business_redirect_uri)
			.append("&sid=").append(sid);
			
			OAuth2 oAuth2 = new OAuth2();
			String codeUrl = oAuth2.getOAuth2CodeUrl(appId, redirect_uri_buffer.toString(), scope, state);
			response.sendRedirect(codeUrl);
			if (Configuration.isDebug()) {
				System.out.println("初始化鉴权接口调用成功，APPID,SCOPE-" + appId + "," + scope);
            }
			return null;
		}else {
			if (Configuration.isDebug()) {
				System.out.println("初始化鉴权接口调用失败");
            }
			throw new WeixinException("鉴权参数不完整（business_redirect_uri、scope）...");
		}
	}
	
	
	public String initUserInfo() throws Exception{

		initDataPramas();
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ path + "/";
		
		if(!Utils.isEmpty(business_redirect_uri) && !Utils.isEmpty(code)) {
			
			WechatComponentParams wechatComponentParams = WechatComponentParamsUtil.getWechatComponentParams();
	        String componentAccessToken = wechatComponentParams.getComponentAccessToken();
	        String result = "";
	       
	        OAuth2 oAuth2 = new OAuth2();
	        OAuth2Token auth2Token = oAuth2.login(appId, componentAccessToken, code);
	        OAuth2User auth2User = oAuth2.getUserInfo();
			if (null != auth2User) {
				result = JSONObject.fromObject(auth2User).toString();
				String params = "&state=" + state + "&sid=" + sid;
				if(business_redirect_uri.indexOf("?") != -1) 
					business_redirect_uri = business_redirect_uri + "&" + params;
				else
					business_redirect_uri = business_redirect_uri + "?" + params;
				
				auth2User.setAppId(appId);
				request.getSession().setAttribute("auth2User", auth2User);
				response.sendRedirect(business_redirect_uri);
				if (Configuration.isDebug()) {
					System.out.println("获取鉴权用户信息接口调用成功");
	            }
				return null;
			}
			if (Configuration.isDebug()) {
				System.out.println("获取鉴权用户信息接口调用失败-1");
            }
			// 重定向后,别忘了返回null值
			return null;
		}else {
			if (Configuration.isDebug()) {
				System.out.println("获取鉴权用户信息接口调用失败-2");
            }
			return "error";
		}
	}

	/**
	 * 初始化接口相关参数
	 * @throws Exception
	 */
	public void initDataPramas() throws Exception {
		if(Utils.isEmpty(sid)) 
			throw new WeixinException("未经授权访问，访问失败...");
		else {
			WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
			if(wechatAuthorizerParams != null) {
				int authorizerStatus = wechatAuthorizerParams.getAuthorizerStatus();//授权状态 1-未授权，2-已授权 
				if(1 == authorizerStatus)
					throw new WeixinException("客户未授权，无法使用此功能...");
				else appId = wechatAuthorizerParams.getAuthorizerAppId();
				
			}else throw new WeixinException("授权码无效...");
		}
	}

	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getBusiness_redirect_uri() {
		return business_redirect_uri;
	}
	public void setBusiness_redirect_uri(String business_redirect_uri) {
		this.business_redirect_uri = business_redirect_uri;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
