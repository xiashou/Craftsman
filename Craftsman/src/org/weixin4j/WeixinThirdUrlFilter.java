/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weixin4j;

import org.weixin4j.util.ConfigurationUtil;
import org.weixin4j.util.TokenUtil;
import org.weixin4j.util.XStreamFactory;

import com.tcode.business.wechat.sys.model.WechatComponentParams;
import com.tcode.business.wechat.sys.util.WechatComponentParamsUtil;
import com.tcode.core.util.Utils;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.weixin4j.spi.IMessageHandler;
import org.weixin4j.aes.WXBizMsgCrypt;
import org.weixin4j.http.OAuthToken;
import org.weixin4j.message.InputMessage;
import org.weixin4j.spi.HandlerFactory;

/**
 * Title: 第三方平台接受微信服务器向其“授权事件接收URL”每隔10分钟定时推送component_verify_ticket
 */
public class WeixinThirdUrlFilter implements Filter {
	
    @Override
    public void init(FilterConfig config) throws ServletException {
    	//服务器启动时初始化一次component_access_token
    	WechatComponentParamsUtil.initWechatComponentParams();
    	
        if (Configuration.isDebug()) {
            System.out.println("WeixinThirdUrlFilter启动成功!");
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        if (Configuration.isDebug()) {
            System.out.println(Utils.getSysTime() + " 获得微信请求:" + request.getMethod() + " 方式");
            System.out.println(Utils.getSysTime() + " 微信请求URL:" + request.getServletPath());
        }
        
        doPost(request, response);
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            //获取POST流
            ServletInputStream in = request.getInputStream();
            if (Configuration.isDebug()) {
                System.out.println("接收到微信输入流,准备处理...");
            }
            
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
    		String xml = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
    		
    		JAXBContext context = JAXBContext.newInstance(InputMessage.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputMessage inputMsg = (InputMessage) unmarshaller.unmarshal(new StringReader(xml));
            
            if (Configuration.isDebug()) {
                System.out.println("解密后明文: " + xml);
                System.out.println("ComponentVerifyTicket：" + inputMsg.getComponentVerifyTicket());
            }
            
            String componentVerifyTicket = inputMsg.getComponentVerifyTicket();
            if(!Utils.isEmpty(componentVerifyTicket)) {
            	//存储最新componentVerifyTicket
            	WechatComponentParams wechatComponentParamsVo = new WechatComponentParams();
            	wechatComponentParamsVo.setComponentAppid(inputMsg.getAppId());
            	wechatComponentParamsVo.setComponentVerifyTicket(componentVerifyTicket);
            	WechatComponentParamsUtil.storeWechatComponentParams(wechatComponentParamsVo);
            	
//            	System.out.println("authToken: " + authToken.getAccess_token());
            	//返回结果
                response.getWriter().write("success");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            response.getWriter().write("");
        }
    }

    @Override
    public void destroy() {
    }
}
