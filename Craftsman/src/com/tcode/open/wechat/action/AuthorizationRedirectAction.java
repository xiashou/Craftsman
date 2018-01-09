package com.tcode.open.wechat.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.util.ConfigurationUtil;

import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.service.WechatAuthorizerParamsService;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.open.wechat.util.AuthorizationRedirectUtil;

import net.sf.json.JSONObject;

/**
 * 授权与回调
 * @author supeng
 *
 */
@Scope("prototype")
@Component("AuthorizationRedirectAction")
public class AuthorizationRedirectAction extends BaseAction{
	
	private WechatAuthorizerParams wechatAuthorizerParams;
	private WechatAuthorizerParamsService wechatAuthorizerParamsService;
	
	/**
	 * 获取预授权码，进入授权页面
	 * @return
	 * @throws Exception
	 */
	public void authorization() throws Exception {
		
		HttpServletResponse response = getResponse();
		HttpServletRequest request = getRequest();
		
		String sid = request.getParameter("sid");//自定义授权码，便于进行产品管理
		
		if(!Utils.isEmpty(sid) && WechatAuthorizerParamsUtil.validateSId(sid)) {//有自定义授权码，且有效（已生成，未被授权使用），则继续进行授权操作，否则无法进行授权，跳转至错误页面
			//更新授权方信息
			WechatAuthorizerParams wechatAuthorizerParamsDto = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
			wechatAuthorizerParamsDto.setMsgSignature(wechatAuthorizerParams.getMsgSignature());
			wechatAuthorizerParamsDto.setAuthorizerAppSecret(wechatAuthorizerParams.getAuthorizerAppSecret());
			wechatAuthorizerParamsService.update(wechatAuthorizerParamsDto);
			
			//获取预授权码
			JSONObject jsonObj = AuthorizationRedirectUtil.initPreAuthCode();
			
			//跳转至授权页面
			Object obj = jsonObj.get("pre_auth_code");
			String appId = ConfigurationUtil.getSystemAppid();
			
	        if (obj != null) {
	        	String preAuthCode = (String) obj;
	        	String redirect_uri = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + ":" 
	        	+ request.getServerPort() + "/open/wechat/sys/authorizationRedirect.atc?sid=" + sid;
	        	String authUrl = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=" + appId + "&pre_auth_code=" + preAuthCode + "&redirect_uri=" + redirect_uri;
	        	response.sendRedirect(authUrl);
	        }
		}else throw new RuntimeException("自定义授权码无效！");
		
	}
	
	/**
	 * 接受授权后微信回调请求
	 * @return
	 * @throws Exception
	 */
	public String authorizationRedirect() throws Exception {
		
		HttpServletRequest request = getRequest();
		String sid = request.getParameter("sid");//自定义授权码，便于进行产品管理
		
		if(!Utils.isEmpty(sid) && WechatAuthorizerParamsUtil.validateSId(sid)) {//有自定义授权码，且有效（已生成，未被授权使用），则继续进行授权操作，否则无法进行授权，跳转至错误页面
			//获取授权码
			String authCode = request.getParameter("auth_code");
			
			//使用授权码换取并存储授权公众号的授权信息,即产品授权
			AuthorizationRedirectUtil.initAuthorizationInfo(authCode, sid, request);
		}else throw new RuntimeException("自定义授权码无效！");
		
		return SUCCESS;
	}

	public WechatAuthorizerParams getWechatAuthorizerParams() {
		return wechatAuthorizerParams;
	}

	public void setWechatAuthorizerParams(WechatAuthorizerParams wechatAuthorizerParams) {
		this.wechatAuthorizerParams = wechatAuthorizerParams;
	}

	public WechatAuthorizerParamsService getWechatAuthorizerParamsService() {
		return wechatAuthorizerParamsService;
	}

	@Resource
	public void setWechatAuthorizerParamsService(WechatAuthorizerParamsService wechatAuthorizerParamsService) {
		this.wechatAuthorizerParamsService = wechatAuthorizerParamsService;
	}
	
}
