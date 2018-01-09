package com.tcode.business.wechat.sys.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.weixin4j.User;
import org.weixin4j.Weixin;
import org.weixin4j.message.event.EventMessage;
import org.weixin4j.message.event.QrsceneSubscribeEventMessage;

import com.tcode.business.wechat.sys.model.WechatAttentionAuthorizer;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.service.WechatAttentionAuthorizerService;
import com.tcode.core.util.Utils;

@Component
public class WechatAttentionAuthorizerUtil {
	
	private static WechatAttentionAuthorizerService wechatAttentionAuthorizerService;
	
	/**
     * 初始化关注信息
     * @param msg
     * @throws Exception
     */
    public static void initWechatAttentionAuthorizer(EventMessage msg) throws Exception {
    	WechatAttentionAuthorizer wechatAttentionAuthorizer = new WechatAttentionAuthorizer();
    	String authorizerAppId = msg.getAppId();//授权方appId
    	String openId = msg.getFromUserName();
    	
    	wechatAttentionAuthorizer.setAuthorizerAppId(authorizerAppId);//授权方appId
    	wechatAttentionAuthorizer.setOpenId(openId);//opeId
    	wechatAttentionAuthorizer.setEvent(msg.getEvent());////事件类型 subscribe，unsubscribe，SCAN，LOCATION，CLICK，VIEW
    	
    	//如果是扫描事件，转换具体事例类型，获取二维码参数
    	if(msg instanceof QrsceneSubscribeEventMessage) 
    		wechatAttentionAuthorizer.setEventKey(((QrsceneSubscribeEventMessage) msg).getEventKey());
    	
    	WechatAttentionAuthorizerUtil.initWechatAttentionAuthorizer(wechatAttentionAuthorizer);
    	
    	
    }
	
	/**
	 * 初始化关注信息，存在进行更新，不存在则进行新增操作
	 * @param wechatAttentionAuthorizerVo
	 * @throws Exception
	 */
	public static void initWechatAttentionAuthorizer(WechatAttentionAuthorizer wechatAttentionAuthorizerVo) throws Exception {
		String authorizerAppId = wechatAttentionAuthorizerVo.getAuthorizerAppId();//授权方appId
		String openId = wechatAttentionAuthorizerVo.getOpenId();
		
		//获取用户信息
    	User user = new Weixin().getUserInfo(authorizerAppId, openId);
    	wechatAttentionAuthorizerVo.setAttentionStatus(Integer.parseInt(user.getSubscribe()));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    	wechatAttentionAuthorizerVo.setNickname(user.getNickname());//用户的昵称
    	wechatAttentionAuthorizerVo.setSex(user.getSex());//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    	wechatAttentionAuthorizerVo.setCountry(user.getCountry());//用户所在国家
    	wechatAttentionAuthorizerVo.setCity(user.getCity());//用户所在城市
    	wechatAttentionAuthorizerVo.setProvince(user.getProvince());//用户所在省份
    	wechatAttentionAuthorizerVo.setPictureUrl(user.getHeadimgurl());//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
    	
    	//获取门店编码
    	WechatAuthorizerParams wechatAuthorizerParams =  WechatAuthorizerParamsUtil.getWechatAuthorizerParamsByAppId(authorizerAppId);
    	if(wechatAuthorizerParams != null) {
    		wechatAttentionAuthorizerVo.setDeptCode(wechatAuthorizerParams.getDeptCode());//门店编码
    		wechatAttentionAuthorizerVo.setDeptName(wechatAuthorizerParams.getDeptName());//门店名称
    	} else {
    		throw new RuntimeException("初始化关注信息时，为找到门店信息！");
    	}
    	
		List<WechatAttentionAuthorizer> wechatAttentionAuthorizerList = wechatAttentionAuthorizerService.getByappIdAndopenId(authorizerAppId, openId);
		if(wechatAttentionAuthorizerList.size() > 0) {//如果存在此人关注信息，则进行更新
			WechatAttentionAuthorizer wechatAttentionAuthorizer = wechatAttentionAuthorizerList.get(0);
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getNickname()))
				wechatAttentionAuthorizer.setNickname(wechatAttentionAuthorizerVo.getNickname());//用户昵称
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getSex()))
				wechatAttentionAuthorizer.setSex(wechatAttentionAuthorizerVo.getSex());//性别 0-未知，1-男，2-女
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getMobile()))
				wechatAttentionAuthorizer.setMobile(wechatAttentionAuthorizerVo.getMobile());//手机
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getCountry()))
				wechatAttentionAuthorizer.setCountry(wechatAttentionAuthorizerVo.getCountry());//国家
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getProvince()))
				wechatAttentionAuthorizer.setProvince(wechatAttentionAuthorizerVo.getProvince());//省份
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getCity()))
				wechatAttentionAuthorizer.setCity(wechatAttentionAuthorizerVo.getCity());//城市
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getPictureUrl()))
				wechatAttentionAuthorizer.setPictureUrl(wechatAttentionAuthorizerVo.getPictureUrl());//用户图像
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getAttentionStatus()))
				wechatAttentionAuthorizer.setAttentionStatus(wechatAttentionAuthorizerVo.getAttentionStatus());//关注状态 0-已关注，1-取消关注
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getEvent()))
				wechatAttentionAuthorizer.setEvent(wechatAttentionAuthorizerVo.getEvent());//事件类型 subscribe，unsubscribe，SCAN，LOCATION，CLICK，VIEW
			if(!Utils.isEmpty(wechatAttentionAuthorizerVo.getEventKey()))
				wechatAttentionAuthorizer.setEventKey(wechatAttentionAuthorizerVo.getEventKey());//事件KEY值 qrscene_为前缀，后面为二维码的参数值
			
	    	wechatAttentionAuthorizerVo.setUpdateBy("sys");
			wechatAttentionAuthorizerService.update(wechatAttentionAuthorizer);
		}else {
			wechatAttentionAuthorizerVo.setCreateBy("sys");
	    	wechatAttentionAuthorizerVo.setUpdateBy("sys");
			wechatAttentionAuthorizerService.insert(wechatAttentionAuthorizerVo);
		}
		
		
	}

	
	public WechatAttentionAuthorizerService getWechatAttentionAuthorizerService() {
		return wechatAttentionAuthorizerService;
	}

	@Resource
	public void setWechatAttentionAuthorizerService(WechatAttentionAuthorizerService wechatAttentionAuthorizerService) {
		this.wechatAttentionAuthorizerService = wechatAttentionAuthorizerService;
	}
	
}
