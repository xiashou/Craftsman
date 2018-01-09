package com.tcode.business.wechat.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wechat_attention_authorizer")
public class WechatAttentionAuthorizer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;		//ID，自增长
	@Column(name="authorizer_appid")
	private String authorizerAppId;		//授权方appid
	@Column(name="dept_code")
	private String deptCode;						//店铺编码
	@Column(name="dept_name")
	private String deptName;				//店铺名称
	@Column(name="openid")
	private String openId;				//用户openid
	@Column(name="nickname")
	private String nickname;				//用户昵称
	@Column(name="sex")
	private int sex;		//性别 0-未知，1-男，2-女
	@Column(name="mobile")
	private String mobile;							//手机
	@Column(name="country")
	private String country;				//国家
	@Column(name="province")
	private String province;					//省份
	@Column(name="city")
	private String city;					//城市
	@Column(name="picture_url")
	private String pictureUrl;				//用户图像
	@Column(name="attention_status")
	private int attentionStatus;	//关注状态 0-未关注/取消关注，1-已关注
	@Column(name="event")
	private String event;					//事件类型 subscribe，unsubscribe，SCAN，LOCATION，CLICK，VIEW
	@Column(name="event_key")
	private String eventKey;					//事件KEY值 qrscene_为前缀，后面为二维码的参数值
	@Column(name="create_by")
	private String createBy;					//创建人
	@Column(name="update_by")
	private String updateBy;					//修改人
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
	public String getAuthorizerAppId() {
		return authorizerAppId;
	}
	public void setAuthorizerAppId(String authorizerAppId) {
		this.authorizerAppId = authorizerAppId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public int getAttentionStatus() {
		return attentionStatus;
	}
	public void setAttentionStatus(int attentionStatus) {
		this.attentionStatus = attentionStatus;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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
