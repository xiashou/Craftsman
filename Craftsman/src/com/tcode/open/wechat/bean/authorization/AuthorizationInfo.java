package com.tcode.open.wechat.bean.authorization;

import java.util.List;

public class AuthorizationInfo {
	
	private String authorizer_appid;
	private String authorizer_access_token;
	private int expires_in;
	private String authorizer_refresh_token;
	private List<FuncInfo> func_info;
	
	public String getAuthorizer_appid() {
		return authorizer_appid;
	}
	public void setAuthorizer_appid(String authorizer_appid) {
		this.authorizer_appid = authorizer_appid;
	}
	public String getAuthorizer_access_token() {
		return authorizer_access_token;
	}
	public void setAuthorizer_access_token(String authorizer_access_token) {
		this.authorizer_access_token = authorizer_access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public String getAuthorizer_refresh_token() {
		return authorizer_refresh_token;
	}
	public void setAuthorizer_refresh_token(String authorizer_refresh_token) {
		this.authorizer_refresh_token = authorizer_refresh_token;
	}
	public List<FuncInfo> getFunc_info() {
		return func_info;
	}
	public void setFunc_info(List<FuncInfo> func_info) {
		this.func_info = func_info;
	}	
	
	/**
	 * 得到授权功能列表字符串信息
	 * @return
	 */
	public String getFuncInfoStr() {
		StringBuffer stringBuffer = new StringBuffer();
		for(FuncInfo funcInfo : func_info) {
			stringBuffer.append(funcInfo.getFuncscope_category().getId()).append(";");
		}
		return stringBuffer.toString();
	}
}
