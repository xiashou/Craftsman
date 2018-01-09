package com.tcode.business.wechat.sys.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.weixin4j.Configuration;
import org.weixin4j.Weixin;
import org.weixin4j.http.OAuthToken;
import org.weixin4j.util.ConfigurationUtil;

import com.tcode.business.wechat.sys.model.WechatComponentParams;
import com.tcode.business.wechat.sys.service.WechatComponentParamsService;
import com.tcode.core.util.Utils;

@Component
public class WechatComponentParamsUtil {
	
	private static WechatComponentParamsService componentParamsService;
	
	/**
	 * 存储最新微信第三方平台参数
	 * @param wechatComponentParamsVo
	 * @return
	 * @throws Exception
	 */
	public static boolean storeWechatComponentParams(WechatComponentParams wechatComponentParamsVo) throws Exception {
		boolean flag = true;
		WechatComponentParams wechatComponentParams = null;
		String currentTime = Utils.getSysTime();
		
		List<WechatComponentParams> componentParamList = componentParamsService.getAll();
		if(componentParamList.size() > 0) 	{//存在则进行更新
			wechatComponentParams = componentParamList.get(0);
			if(!Utils.isEmpty(wechatComponentParamsVo.getComponentVerifyTicket())) {
				wechatComponentParams.setComponentVerifyTicket(wechatComponentParamsVo.getComponentVerifyTicket());
				wechatComponentParams.setTicketFreshTime(currentTime);
			}
			if(!Utils.isEmpty(wechatComponentParamsVo.getComponentAccessToken())) {
				wechatComponentParams.setComponentAccessToken(wechatComponentParamsVo.getComponentAccessToken());
				wechatComponentParams.setTokenFreshTime(currentTime);
				wechatComponentParams.setTokenFreshRate(wechatComponentParamsVo.getTokenFreshRate());
			}
			
			componentParamsService.update(wechatComponentParams);
		}else {//新增
			if(!Utils.isEmpty(wechatComponentParamsVo.getComponentVerifyTicket())) 
				wechatComponentParamsVo.setTicketFreshTime(currentTime);
			
			if(!Utils.isEmpty(wechatComponentParamsVo.getComponentAccessToken())) 
				wechatComponentParamsVo.setTokenFreshTime(currentTime);
			
			componentParamsService.insert(wechatComponentParamsVo);
		}
		
		return flag;
	}
	
	/**
	 * 获取微信第三方平台参数
	 * @return
	 * @throws Exception
	 */
	public static WechatComponentParams getWechatComponentParams() throws Exception {
		WechatComponentParams wechatComponentParams = null;
		List<WechatComponentParams> componentParamList = componentParamsService.getAll();
		if(componentParamList.size() > 0) 	
			wechatComponentParams = componentParamList.get(0);
		
		return wechatComponentParams;
	}
	
	/**
	 * 初始化第三方平台component_access_token参数
	 */
	public static OAuthToken initWechatComponentParams() {
		WechatComponentParams wechatComponentParams;
		OAuthToken authToken = null;
		try {
			wechatComponentParams = WechatComponentParamsUtil.getWechatComponentParams();
			if(wechatComponentParams != null) {
				String componentVerifyTicket = wechatComponentParams.getComponentVerifyTicket();
				if(wechatComponentParams != null && !Utils.isEmpty(componentVerifyTicket)) {//有一条数据，即只有在收到微信推送的component_verify_ticket才能进行操作
		    		String appId = ConfigurationUtil.getSystemAppid();
		    		String componentAccessToken = wechatComponentParams.getComponentAccessToken();
		    		if(Utils.isEmpty(componentAccessToken))//如果componentAccessToken为空则直接通过接口获取
		    			authToken = new Weixin().login(appId, ConfigurationUtil.getSystemAppSecret(), componentVerifyTicket);
		    		else {//不为空则判断是否失效，失效重新获取，未失效不做操作
		    			String nowDate = Utils.getSysTime();
		    			String tokenFreshTime = wechatComponentParams.getTokenFreshTime();
		    			int tokenFreshRate = wechatComponentParams.getTokenFreshRate();
		    			int tokenFreshRateCache = tokenFreshRate - 1200;//提前20分钟刷新
		    			if(Utils.secondBetween(tokenFreshTime, nowDate, "yyyy/MM/dd HH:mm:ss") >= tokenFreshRateCache)//失效 
		    				authToken = new Weixin().login(appId, ConfigurationUtil.getSystemAppSecret(), componentVerifyTicket);
		    			else {//未失效
		    				authToken = new OAuthToken(componentAccessToken, tokenFreshRate);
		    			}
		    		}
		    	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return authToken;
	}
	

	
	public WechatComponentParamsService getComponentParamsService() {
		return componentParamsService;
	}

	@Resource
	public void setComponentParamsService(WechatComponentParamsService componentParamsService) {
		WechatComponentParamsUtil.componentParamsService = componentParamsService;
	}
	
}
