package com.tcode.open.wechat.bean.jssdk;

public class WXConfig {
	
	private String appId;// 必填，公众号的唯一标识
	private String timestamp; // 必填，生成签名的时间戳
	private String nonceStr; // 必填，生成签名的随机串
	private String signature;// 必填，签名，见附录1
	private String jsApiList;// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	private String url;//请求JSSDK页面url
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getJsApiList() {
		return jsApiList;
	}
	public void setJsApiList(String jsApiList) {
		this.jsApiList = jsApiList;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
