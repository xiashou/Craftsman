package com.tcode.business.wechat.sys.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
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
import com.tcode.core.util.Utils;

@Component
public class WechatAuthorizerParamsUtil {
	
	private static Logger log = Logger.getLogger("SLog");
	
	private static WechatAuthorizerParamsService wechatAuthorizerParamsService;
	
	/**
	 * 初始化授权令牌authorizer_access_token参数
	 */
	public static void initWechatAuthorizerParams() {
		try {
			//获取所有已经获得产品授权的授权方信息
			int authorizerStatus = 2;//授权状态 1-未授权，2-已授权
			List<WechatAuthorizerParams> wechatAuthorizerParamList = wechatAuthorizerParamsService.getByAuthorizerStatus(authorizerStatus);
			
			//遍历集合，授权方令牌已经过期的信息进行刷新
			for(WechatAuthorizerParams authorizerParams : wechatAuthorizerParamList) {
				String nowDate = Utils.getSysTime();
				String tokenFreshTime = authorizerParams.getTokenFreshTime();
				int tokenFreshRate = authorizerParams.getTokenFreshRate();
				int tokenFreshRateCache = tokenFreshRate - 1200;//提前20分钟刷新
				if(Utils.secondBetween(tokenFreshTime, nowDate, "yyyy/MM/dd HH:mm:ss") >= tokenFreshRateCache)//失效 
					refreshAuthorizerToken(authorizerParams);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 刷新授权方令牌authorizer_access_token
	 * @param wechatAuthorizerParams
	 */
	public static void refreshAuthorizerToken(WechatAuthorizerParams wechatAuthorizerParams) {
		try {
			WechatComponentParams wechatComponentParams = WechatComponentParamsUtil.getWechatComponentParams();
			if (wechatComponentParams != null && !Utils.isEmpty(wechatComponentParams.getComponentAccessToken())) {
				String appId = ConfigurationUtil.getSystemAppid();//第三方平台appid
				String componentAccessToken = wechatComponentParams.getComponentAccessToken();
				String authorizerAppId = wechatAuthorizerParams.getAuthorizerAppId();//授权方appid
				String authorizerRefreshToken = wechatAuthorizerParams.getAuthorizerRefreshToken();//授权方的刷新令牌
				// 拼接参数
				JSONObject postParams = new JSONObject();
				postParams.put("component_appid", appId);
				postParams.put("authorizer_appid", authorizerAppId);
				postParams.put("authorizer_refresh_token", authorizerRefreshToken);
				// 创建请求对象
				HttpsClient http = new HttpsClient();
				// 获取预授权码pre_auth_code
				Response res = http
						.post("https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token="
								+ componentAccessToken, postParams);
		
				// 根据请求结果判定，是否验证成功
				JSONObject jsonObj = res.asJSONObject();
				if (jsonObj != null) {
					if (Configuration.isDebug()) {
						System.out.println("刷新授权方令牌返回json：" + jsonObj.toString());
					}
					Object errcode = jsonObj.get("errcode");
					if (errcode != null) {
						// 返回异常信息
						log.error(authorizerAppId + "|" + errcode.toString());
						throw new WeixinException(authorizerAppId + "|" + errcode.toString());
					}
					Object obj = jsonObj.get("authorizer_access_token");
					
					if (obj != null) {
						
						//存储最新authorizerAccessToken
						String currentTime = Utils.getSysTime();
						String authorizerAccessToken = (String) obj;
						int expiresIn = (int) jsonObj.get("expires_in");
						authorizerRefreshToken = (String) jsonObj.get("authorizer_refresh_token");
						
						wechatAuthorizerParams.setAuthorizerAccessToken(authorizerAccessToken);
						wechatAuthorizerParams.setTokenFreshTime(currentTime);//Token刷新时间
						wechatAuthorizerParams.setTokenFreshRate(expiresIn);//Token刷新频率
						wechatAuthorizerParams.setAuthorizerRefreshToken(authorizerRefreshToken);//刷新令牌
						
						wechatAuthorizerParamsService.update(wechatAuthorizerParams);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  根据授权方appId获得令牌
	 * @param authorizerAppId
	 * @return
	 * @throws Exception 
	 */
	public static String getAuthorizerAccessTokenByAppId(String authorizerAppId) throws Exception {
		List<WechatAuthorizerParams> wechatAuthorizerParamsList = wechatAuthorizerParamsService.getByAuthorizerAppId(authorizerAppId);
		String authorizerAccessToken = null;
		
		if(wechatAuthorizerParamsList.size() == 1) {
			WechatAuthorizerParams wechatAuthorizerParams = wechatAuthorizerParamsList.get(0);
			authorizerAccessToken = wechatAuthorizerParams.getAuthorizerAccessToken();
		}
		
		return authorizerAccessToken;
	}
	
	/**
	 * 根据门店编码得到授权方参数信息
	 * @param depNo
	 * @return
	 * @throws Exception
	 */
	public static WechatAuthorizerParams getWechatAuthorizerParamsByDeptCode(String deptCode) throws Exception {
		List<WechatAuthorizerParams> wechatAuthorizerParamsList = wechatAuthorizerParamsService.getByDeptCode(deptCode);
		WechatAuthorizerParams wechatAuthorizerParams = null;
		
		if(wechatAuthorizerParamsList.size() == 1) 
			wechatAuthorizerParams = wechatAuthorizerParamsList.get(0);
		
		return wechatAuthorizerParams;
	}
	
	/**
	 * 根据公司ID得到授权方参数信息
	 * @param depNo
	 * @return
	 * @throws Exception
	 */
	public static WechatAuthorizerParams getWechatAuthorizerParamsByCompanyId(String companyId) throws Exception {
		List<WechatAuthorizerParams> wechatAuthorizerParamsList = wechatAuthorizerParamsService.getByCompanyId(companyId);
		WechatAuthorizerParams wechatAuthorizerParams = null;
		
		if(wechatAuthorizerParamsList.size() > 0) 
			wechatAuthorizerParams = wechatAuthorizerParamsList.get(0);
		
		return wechatAuthorizerParams;
	}
	
	/**
	 * 根据授权方appId得到授权方参数信息
	 * @param authorizerAppId
	 * @return
	 * @throws Exception
	 */
	public static WechatAuthorizerParams getWechatAuthorizerParamsByAppId(String authorizerAppId) throws Exception {
		List<WechatAuthorizerParams> wechatAuthorizerParamsList = wechatAuthorizerParamsService.getByAuthorizerAppId(authorizerAppId);
		WechatAuthorizerParams wechatAuthorizerParams = null;
		
		if(wechatAuthorizerParamsList.size() == 1) {
			wechatAuthorizerParams = wechatAuthorizerParamsList.get(0);
		}
		
		return wechatAuthorizerParams;
	}
	
	/**
	 * 根据自定义授权码得到授权方参数信息
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public static WechatAuthorizerParams getWechatAuthorizerParamsBySid(String sid) throws Exception {
		List<WechatAuthorizerParams> wechatAuthorizerParamsList = wechatAuthorizerParamsService.getBySId(sid);
		WechatAuthorizerParams wechatAuthorizerParams = null;
		
		if(wechatAuthorizerParamsList.size() == 1) {
			wechatAuthorizerParams = wechatAuthorizerParamsList.get(0);
		}
		
		return wechatAuthorizerParams;
	}
	
	/**
	 * 验证自定义授权码是否有效（有效存在且未被授权使用）
	 * 
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public static boolean validateSId(String sid) throws Exception {
		boolean flag = false;
		List<WechatAuthorizerParams> authorizerParamList = wechatAuthorizerParamsService.getBySId(sid);
		if (authorizerParamList.size() == 1) {
			WechatAuthorizerParams wechatAuthorizerParams = authorizerParamList.get(0);
			if (1 == wechatAuthorizerParams.getAuthorizerStatus()) // 授权状态 1-未授权，2-已授权
				flag = true;

		}
		return flag;
	}
	
	/**
	 * 验证自定义授权码是否有效（有效存在且已成功授权使用）
	 * 
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public static boolean validateSIdS(String sid) throws Exception {
		boolean flag = false;
		List<WechatAuthorizerParams> authorizerParamList = wechatAuthorizerParamsService.getBySId(sid);
		if (authorizerParamList.size() == 1) {
			WechatAuthorizerParams wechatAuthorizerParams = authorizerParamList.get(0);
			if (2 == wechatAuthorizerParams.getAuthorizerStatus()) // 授权状态 1-未授权，2-已授权
				flag = true;

		}
		return flag;
	}
	
	public static boolean initMenu(HttpServletRequest request, String sid, String appId ) throws Exception {
		String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		Menu menu = new Menu();
		List<SingleButton> button = new ArrayList<SingleButton>();
		//一级菜单 1
		SingleButton viewButtonOneL1 = new ViewButton("在线商城", basePath + "/open/wechat/biz/auth/initMallIndex.atc?sid=" + sid);
		//一级菜单 2
		SingleButton viewButtonOneL2 = new ViewButton("个人中心", basePath + "/open/wechat/biz/auth/initCenter.atc?sid=" + sid);
		//一级菜单 2
		SingleButton viewButtonOneL3 = new ViewButton("最新活动", basePath + "/open/wechat/biz/auth/initActSignupList.atc?auth=0&sid=" + sid);
		
		
//		//一级菜单 2
//		SingleButton buttonOneL2 = new SingleButton();
//		buttonOneL2.setName("更多服务");
//		
//		//二级菜单 2-1
//		SingleButton subViewButton = new ViewButton("联系我们", basePath + "/wct/contact.jsp?sid=" + sid);
//		SingleButton subViewButton1 = new ViewButton("活动报名", basePath + "/open/wechat/biz/auth/initActSignupList.atc?auth=0&sid=" + sid);
//		List<SingleButton> sub_buttonList = new ArrayList<SingleButton>();
//		sub_buttonList.add(subViewButton);
//		sub_buttonList.add(subViewButton1);
//		buttonOneL2.setSubButton(sub_buttonList);
		
		button.add(viewButtonOneL1);
		button.add(viewButtonOneL2);
		button.add(viewButtonOneL3);
//		button.add(buttonOneL2);
		menu.setButton(button);
		
		Weixin weixin = new Weixin();
		weixin.createMenu(menu, appId);
		
		return true;
	}
	
	
	public WechatAuthorizerParamsService getWechatAuthorizerParamsService() {
		return wechatAuthorizerParamsService;
	}

	@Resource
	public void setWechatAuthorizerParamsService(WechatAuthorizerParamsService wechatAuthorizerParamsService) {
		WechatAuthorizerParamsUtil.wechatAuthorizerParamsService = wechatAuthorizerParamsService;
	}
	
	
}
