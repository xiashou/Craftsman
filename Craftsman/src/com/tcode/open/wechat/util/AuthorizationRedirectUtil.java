package com.tcode.open.wechat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.weixin4j.Configuration;
import org.weixin4j.Menu;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.http.HttpsClient;
import org.weixin4j.http.Response;
import org.weixin4j.menu.SingleButton;
import org.weixin4j.menu.ViewButton;
import org.weixin4j.util.ConfigurationUtil;

import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.model.WechatComponentParams;
import com.tcode.business.wechat.sys.service.WechatAuthorizerParamsService;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.business.wechat.sys.util.WechatComponentParamsUtil;
import com.tcode.core.util.Utils;
import com.tcode.open.wechat.bean.authorization.Authorization;
import com.tcode.open.wechat.bean.authorization.AuthorizationInfo;
import com.tcode.open.wechat.bean.authorization.FuncInfo;
import com.tcode.open.wechat.bean.authorization.FuncscopeCategory;

import net.sf.json.JSONObject;

/**
 * 授权与回调工具类
 * 
 * @author supeng
 *
 */
@Component
public class AuthorizationRedirectUtil {

	private static WechatAuthorizerParamsService wechatAuthorizerParamsService;

	/**
	 * 获取预授权码
	 * 
	 * @return
	 * @throws Exception
	 */
	public static JSONObject initPreAuthCode() throws Exception {
		JSONObject jsonObj = null;
		WechatComponentParams wechatComponentParams = WechatComponentParamsUtil.getWechatComponentParams();
		if (wechatComponentParams != null && !Utils.isEmpty(wechatComponentParams.getComponentAccessToken())) {
			String appId = ConfigurationUtil.getSystemAppid();
			String componentAccessToken = wechatComponentParams.getComponentAccessToken();
			// 拼接参数
			JSONObject postParams = new JSONObject();
			postParams.put("component_appid", appId);
			// 创建请求对象
			HttpsClient http = new HttpsClient();
			// 获取预授权码pre_auth_code
			Response res = http
					.post("https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token="
							+ componentAccessToken, postParams);

			// 根据请求结果判定，是否验证成功
			jsonObj = res.asJSONObject();
			if (jsonObj != null) {
				if (Configuration.isDebug()) {
					System.out.println("pre_auth返回json：" + jsonObj.toString());
				}
				Object errcode = jsonObj.get("errcode");
				if (errcode != null) {
					// 返回异常信息
					throw new WeixinException(errcode.toString());
				}
			}
		}
		return jsonObj;
	}

	/**
	 * 使用授权码换取授权公众号的授权信息
	 * 
	 * @param authCode
	 *            授权码
	 * @throws Exception
	 */
	public static void initAuthorizationInfo(String authCode, String sid, HttpServletRequest request) throws Exception {
		// 换取公众号的接口调用凭据(authorizer_access_token)和授权信息
		WechatComponentParams wechatComponentParams = WechatComponentParamsUtil.getWechatComponentParams();
		if (wechatComponentParams != null && !Utils.isEmpty(wechatComponentParams.getComponentAccessToken())) {
			String appId = ConfigurationUtil.getSystemAppid();
			String componentAccessToken = wechatComponentParams.getComponentAccessToken();
			// 拼接参数
			JSONObject postParams = new JSONObject();
			postParams.put("component_appid", appId);
			postParams.put("authorization_code", authCode);
			// 创建请求对象
			HttpsClient http = new HttpsClient();
			// 获取预授权码pre_auth_code
			Response res = http
					.post("https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token="
							+ componentAccessToken, postParams);

			// 根据请求结果判定，是否验证成功
			JSONObject jsonObj = res.asJSONObject();
			if (jsonObj != null) {
				if (Configuration.isDebug()) {
					System.out.println("换取公众号的接口调用凭据返回json：" + jsonObj.toString());
				}
				Object errcode = jsonObj.get("errcode");
				if (errcode != null) {
					// 返回异常信息
					throw new WeixinException(errcode.toString());
				}
				Object obj = jsonObj.get("authorization_info");

				if (obj != null) {
					Map<String, Class> classMap = new HashMap<>();
					classMap.put("func_info", FuncInfo.class);
					classMap.put("funcscope_category", FuncscopeCategory.class);
					Authorization authorization = (Authorization) jsonObj.toBean(jsonObj, Authorization.class, classMap);
					
					//产品授权
					authorizationProduct(authorization, sid);
					
					//创建菜单
					WechatAuthorizerParamsUtil.initMenu(request, sid, authorization.getAuthorization_info().getAuthorizer_appid());
				}
			}
		}
	}
	
	/**
	 * 产品授权
	 * @param authorization
	 * @throws Exception 
	 */
	public static void authorizationProduct(Authorization authorization, String sid) throws Exception {
		String currentTime = Utils.getSysTime();
		AuthorizationInfo authorizationInfo = authorization.getAuthorization_info();
		boolean flag = false;//是否可以授权
		
		//根据authorizerAppId获取授权信息
		String authorizerAppId = authorizationInfo.getAuthorizer_appid();
		List<WechatAuthorizerParams> wechatAuthorizerParamList = wechatAuthorizerParamsService.getByAuthorizerAppId(authorizerAppId);
		
		if(wechatAuthorizerParamList.size() <= 0) {//未授权
			flag = true;
		}else {
			//如该appid对应的sid与当前授权的sid不一致，说明错位授权，则提示错位授权，取消操作
			WechatAuthorizerParams wechatAuthorizerParams = wechatAuthorizerParamList.get(0);
			String sids = wechatAuthorizerParams.getSid();
			if(!sid.equalsIgnoreCase(sids))
				throw new RuntimeException("授权应用与授权码不对应！");
			else {//再次授权，更新权限等相关信息
				flag = true;
			}
		}
		
		if(flag) {
			List<WechatAuthorizerParams> authorizerParamList = wechatAuthorizerParamsService.getBySId(sid);
			if (authorizerParamList.size() == 1) {
				WechatAuthorizerParams wechatAuthorizerParams = authorizerParamList.get(0);
				wechatAuthorizerParams.setAuthorizerStatus(2);//授权状态 1-未授权，2-已授权
				wechatAuthorizerParams.setAuthorizerAppId(authorizationInfo.getAuthorizer_appid());////授权方appid
				wechatAuthorizerParams.setAuthorizerAccessToken(authorizationInfo.getAuthorizer_access_token());//授权方接口调用凭据（在授权的公众号具备API权限时，才有此返回值），也简称为令牌
				wechatAuthorizerParams.setTokenFreshTime(currentTime);//Token刷新时间
				wechatAuthorizerParams.setTokenFreshRate(authorizationInfo.getExpires_in());////Token刷新频率
				wechatAuthorizerParams.setAuthorizerRefreshToken(authorizationInfo.getAuthorizer_refresh_token());//接口调用凭据刷新令牌
				wechatAuthorizerParams.setFuncInfo(authorizationInfo.getFuncInfoStr());//公众号授权给开发者的权限集列表
				
				wechatAuthorizerParamsService.update(wechatAuthorizerParams);
			}else 
				throw new RuntimeException("授权码重复！");
		}
		
	}
	
	
	public WechatAuthorizerParamsService getWechatAuthorizerParamsService() {
		return wechatAuthorizerParamsService;
	}

	@Resource
	public void setWechatAuthorizerParamsService(WechatAuthorizerParamsService wechatAuthorizerParamsService) {
		this.wechatAuthorizerParamsService = wechatAuthorizerParamsService;
	}

}
