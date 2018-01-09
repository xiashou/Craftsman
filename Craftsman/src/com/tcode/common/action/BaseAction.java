package com.tcode.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.weixin4j.OAuth2User;

import com.opensymphony.xwork2.ActionSupport;
import com.tcode.business.member.model.Member;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.core.util.Constant;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysRole;
import com.tcode.system.model.SysUser;

public class BaseAction extends ActionSupport implements Constant {

	private static final long serialVersionUID = 6789383939256357567L;

	private HttpServletResponse response = ServletActionContext.getResponse();
	private HttpServletRequest request = ServletActionContext.getRequest();

	private Boolean success;
	private String msg;

	private Integer start;
	private Integer limit;
	private Integer totalCount;
	
	/**
	 * 获取系统当前操作人
	 * @return
	 */
	public SysUser getUser() {
		SysUser user = (SysUser) request.getSession().getAttribute(SESSION_USER);
		return user;
	}
	
	/**
	 * 获取系统操作角色
	 * @return
	 */
	public SysRole getRole() {
		SysRole role = (SysRole) request.getSession().getAttribute(SESSION_ROLE);
		return role;
	}
	
	/**
	 * 获取系统操作部门
	 * @return
	 */
	public SysDept getDept() {
		SysDept dept = (SysDept) request.getSession().getAttribute(SESSION_DEPT);
		return dept;
	}
	
	/**
	 * 获取部门公众号绑定信息
	 * @return
	 */
	public WechatAuthorizerParams getWechatApp() {
		WechatAuthorizerParams wechatAuthorizerParams = (WechatAuthorizerParams) request.getSession().getAttribute(SESSION_WECHATAPP);
		return wechatAuthorizerParams;
	}

	/**
	 * 获取微信鉴权用户信息
	 * @return
	 */
	public OAuth2User getOAuth2User() {
		OAuth2User oAuth2User = (OAuth2User) request.getSession().getAttribute(AUTH2USER);
		return oAuth2User;
	}
	
	/**
	 * 获取会员信息
	 * @return
	 */
	public Member getMember() {
		Member member = (Member) request.getSession().getAttribute(MEMBER);
		return member;
	}
	
	public void setResult(Boolean success, String msg) {
		this.setSuccess(success);
		this.setMsg(msg);
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
