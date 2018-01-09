package com.tcode.common.jcrontab.job.wechat;

import org.weixin4j.Configuration;
import org.weixin4j.http.OAuthToken;

import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.business.wechat.sys.util.WechatComponentParamsUtil;

public class WechatParamsJob {
	
	public static void main(String[] args) {
		//初始化第三方平台component_access_token参数
		OAuthToken authToken = WechatComponentParamsUtil.initWechatComponentParams();
		if (Configuration.isDebug()) {
            System.out.println("WechatParamsJob，component_access_token初始化成功!");
        }
		
		//初始化第三方平台Authorizer_access_token参数
		WechatAuthorizerParamsUtil.initWechatAuthorizerParams();
		if (Configuration.isDebug()) {
            System.out.println("WechatParamsJob，authorizer_access_token初始化成功!");
        }
	}
	
}
