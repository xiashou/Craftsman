package com.tcode.business.wechat.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信第三方平台参数
 * @author supeng
 *
 */
@Entity
@Table(name="wechat_component_params")
public class WechatComponentParams {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id; //主键
	@Column(name="component_appid")
	private String componentAppid; //第三方平台appid
	@Column(name="component_verify_ticket")
	private String componentVerifyTicket; //第三方平台component_verify_ticket
	@Column(name="component_access_token")
	private String componentAccessToken; //第三方平台component_access_token
	@Column(name="ticket_fresh_time")
	private String ticketFreshTime; //Ticket刷新时间
	@Column(name="token_fresh_time")
	private String tokenFreshTime; //Token刷新时间
	@Column(name="token_fresh_rate")
	private int tokenFreshRate; //Token刷新频率
	@Column(name="create_time")
	private String createTime;//创建时间
	@Column(name="update_time")
	private String updateTime;//修改时间
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComponentAppid() {
		return componentAppid;
	}
	public void setComponentAppid(String componentAppid) {
		this.componentAppid = componentAppid;
	}
	public String getComponentVerifyTicket() {
		return componentVerifyTicket;
	}
	public void setComponentVerifyTicket(String componentVerifyTicket) {
		this.componentVerifyTicket = componentVerifyTicket;
	}
	public String getComponentAccessToken() {
		return componentAccessToken;
	}
	public void setComponentAccessToken(String componentAccessToken) {
		this.componentAccessToken = componentAccessToken;
	}
	public String getTicketFreshTime() {
		return ticketFreshTime;
	}
	public void setTicketFreshTime(String ticketFreshTime) {
		this.ticketFreshTime = ticketFreshTime;
	}
	public String getTokenFreshTime() {
		return tokenFreshTime;
	}
	public void setTokenFreshTime(String tokenFreshTime) {
		this.tokenFreshTime = tokenFreshTime;
	}
	public int getTokenFreshRate() {
		return tokenFreshRate;
	}
	public void setTokenFreshRate(int tokenFreshRate) {
		this.tokenFreshRate = tokenFreshRate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
