package com.tcode.open.wechat.action;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.Configuration;
import org.weixin4j.aes.WXBizMsgCrypt;
import org.weixin4j.spi.HandlerFactory;
import org.weixin4j.spi.IMessageHandler;
import org.weixin4j.util.ConfigurationUtil;
import org.weixin4j.util.TokenUtil;
import org.weixin4j.util.XStreamFactory;

import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

/**
 * 处理授权者业务
 * @author supeng
 *
 */
@Scope("prototype")
@Component("AuthorizerBizAction")
public class AuthorizerBizAction extends BaseAction {
	
	/**
	 * 处理授权者业务入口
	 * @throws Exception
	 */
	public void croeAuthorizerBiz() throws Exception {
		
		HttpServletResponse response = getResponse();
		HttpServletRequest request = getRequest();
		ServletInputStream in = request.getInputStream();
		
		//获取授权方APPId
		String requestUri = request.getRequestURI();
		String reqStr[] = requestUri.split("/");
		String authorizerAppId = reqStr[4];
		
		//处理输入消息，返回结果
        String appId = ConfigurationUtil.getSystemAppid();
        String token = TokenUtil.get();
        String msgSignature = request.getParameter("msg_signature"); //消息体签名，用于验证消息体的正确性
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");       // 随机数
        String SymmetricKey = ConfigurationUtil.getSystemSymmetricKey(); //公众号消息加解密Key 也称为EncodingAESKey
        String fromXML = XStreamFactory.inputStream2String(in);
        
        // 第三方收到公众号平台发送的消息
        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, SymmetricKey, appId);
		String decryptXml = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		
		if (Configuration.isDebug()) {
			System.out.println(Utils.getSysTime() + " 授权方APPID:" + authorizerAppId);
            System.out.println(Utils.getSysTime() + " 授权方接入:" + request.getMethod() + " 方式");
            System.out.println(Utils.getSysTime() + " 授权方接入请求URL:" + request.getServletPath());
            System.out.println("解密前授权方接入xml:" + fromXML);
            System.out.println("解密后授权方接入xml:" + decryptXml);
        }
		
		IMessageHandler messageHandler = HandlerFactory.getMessageHandler();
		//处理输入消息，返回结果
        String replyXml = messageHandler.invoke(decryptXml, authorizerAppId);
        //加密返回结果
        String encryptReplyXml = pc.encryptMsg(replyXml, timestamp, nonce);
        //返回结果
        response.getWriter().write(encryptReplyXml);
	}

}
