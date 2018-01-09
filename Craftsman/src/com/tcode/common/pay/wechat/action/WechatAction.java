package com.tcode.common.pay.wechat.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.mall.service.MallOrderService;
import com.tcode.business.mall.service.MallSettingService;
import com.tcode.business.wechat.act.model.ActJoiner;
import com.tcode.business.wechat.act.service.ActJoinerService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.pay.wechat.util.WXSignUtils;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("wechatAction")
public class WechatAction extends BaseAction {

	private static final long serialVersionUID = -3639957063004417834L;
	private static Logger log = Logger.getLogger("PLog");
	
	private MallSettingService mallSettingService;
	private MallOrderService mallOrderService;
	private ActJoinerService actJoinerService;
	
	private String code;
	private String msg;
	private Object result;
	private String sid;


	/**
	 * 微信回调
	 * @return
	 * @throws Exception
	 */
	public String weixinNotify() {
		InputStream inputStream = null;
		try {
			PrintWriter out = this.getResponse().getWriter();
			// 解析结果存储在HashMap
			Map<String, String> map = new HashMap<String, String>();
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			inputStream = this.getRequest().getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			@SuppressWarnings("unchecked")
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
				if(!e.getName().equals("sign"))
					parameters.put(e.getName(), e.getText());
			}
			JSONObject json = JSONObject.fromObject(map);
			log.warn(json);
			
			// 判断是否支付成功
			if (map.get("return_code").equals("SUCCESS")) {
				
				String appid = map.get("appid").toString();
				if(!Utils.isEmpty(appid)){
					MallSetting setting = mallSettingService.getById(appid);
					if(!Utils.isEmpty(setting)){
						String verifySign = WXSignUtils.createSign("UTF-8", parameters, setting.getAppSecret());
						//验证签名
						if(verifySign.equals(map.get("sign"))){
							String orderNo = map.get("out_trade_no").toString();
							if(!orderNo.substring(0, 1).equals("A")){
								MallOrderHead orderHead = mallOrderService.getOrderHeadById(map.get("out_trade_no").toString());
								if(!Utils.isEmpty(orderHead)){
									if(orderHead.getStatus() == 1)
										orderHead.setStatus(2);
									mallOrderService.updateHead(orderHead);
									log.warn("W_Pay_Success:" + map.get("transaction_id") + "|" + orderHead.getOrderId());
								} else
									log.warn("W_Noexist Error:" + map.get("out_trade_no").toString());
							} else {
								ActJoiner joiner = actJoinerService.getByOrderNo(map.get("out_trade_no").toString());
								if(!Utils.isEmpty(joiner)){
									if(joiner.getStatus() != 1){
										joiner.setStatus(1);
										actJoinerService.update(joiner);
									}
									log.warn("W_Pay_Success:" + map.get("transaction_id") + "|" + joiner.getOrderNo());
								}
							}
							out.print(returnXml("SUCCESS", "OK"));
						} else {
							log.warn("W_Verify Fail!");
							out.print(returnXml("FAIL", "NOTOK"));
						}
					} else
						log.warn("W_Nosetting Error!" + map.get("appid").toString());
				} else
					log.warn("W_Noappid Error!");
			}
			if (map.get("return_code").equals("FAIL")) {
				log.warn("W_"+map.get("return_code"));
				if(inputStream != null){
					inputStream.close();
					inputStream = null;
				}
				out.print(returnXml("FAIL", "NOTOK"));
			}
			if(inputStream != null){
				inputStream.close();
				inputStream = null;
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			return null;
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				inputStream = null;
			}
		}
		return null;
	}
	
	/**
	 * 微信回调
	 * @return
	 * @throws Exception
	 */
	public String weixinActNotify() {
		InputStream inputStream = null;
		try {
			PrintWriter out = this.getResponse().getWriter();
			// 解析结果存储在HashMap
			Map<String, String> map = new HashMap<String, String>();
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			inputStream = this.getRequest().getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			@SuppressWarnings("unchecked")
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
				if(!e.getName().equals("sign"))
					parameters.put(e.getName(), e.getText());
			}
			JSONObject json = JSONObject.fromObject(map);
			log.warn(json);
			
			// 判断是否支付成功
			if (map.get("return_code").equals("SUCCESS")) {
				
				String appid = map.get("appid").toString();
				if(!Utils.isEmpty(appid)){
					MallSetting setting = mallSettingService.getById(appid);
					if(!Utils.isEmpty(setting)){
						String verifySign = WXSignUtils.createSign("UTF-8", parameters, setting.getAppSecret());
						//验证签名
						if(verifySign.equals(map.get("sign"))){
							
							MallOrderHead orderHead = mallOrderService.getOrderHeadById(map.get("out_trade_no").toString());
							if(!Utils.isEmpty(orderHead)){
								if(orderHead.getStatus() == 1)
									orderHead.setStatus(2);
								mallOrderService.updateHead(orderHead);
								log.warn("W_Pay_Success:" + map.get("transaction_id") + "|" + orderHead.getOrderId());
							} else
								log.warn("W_Noexist Error:" + map.get("out_trade_no").toString());
							out.print(returnXml("SUCCESS", "OK"));
						} else {
							log.warn("W_Verify Fail!");
							out.print(returnXml("FAIL", "NOTOK"));
						}
					} else
						log.warn("W_Nosetting Error!" + map.get("appid").toString());
				} else
					log.warn("W_Noappid Error!");
			}
			if (map.get("return_code").equals("FAIL")) {
				log.warn("W_"+map.get("return_code"));
				if(inputStream != null){
					inputStream.close();
					inputStream = null;
				}
				out.print(returnXml("FAIL", "NOTOK"));
			}
			if(inputStream != null){
				inputStream.close();
				inputStream = null;
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			return null;
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				inputStream = null;
			}
		}
		return null;
	}
	
	public static String returnXml(String return_code, String return_msg){  
        return "<xml><return_code><![CDATA["+return_code+"]]></return_code><return_msg><![CDATA["+return_msg+"]]></return_msg></xml>";  
	}
	
	public void setResult(String code, String msg, Object result) {
		this.setCode(code);
		this.setMsg(msg);
		this.setResult(result);
	}
	

	public MallSettingService getMallSettingService() {
		return mallSettingService;
	}
	@Resource
	public void setMallSettingService(MallSettingService mallSettingService) {
		this.mallSettingService = mallSettingService;
	}

	public MallOrderService getMallOrderService() {
		return mallOrderService;
	}
	@Resource
	public void setMallOrderService(MallOrderService mallOrderService) {
		this.mallOrderService = mallOrderService;
	}

	public ActJoinerService getActJoinerService() {
		return actJoinerService;
	}
	@Resource
	public void setActJoinerService(ActJoinerService actJoinerService) {
		this.actJoinerService = actJoinerService;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}

}
