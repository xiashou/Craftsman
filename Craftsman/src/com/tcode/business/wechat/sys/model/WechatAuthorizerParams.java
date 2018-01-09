package com.tcode.business.wechat.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信授权方参数
 * @author supeng
 *
 */
@Entity
@Table(name="wechat_authorizer_params")
public class WechatAuthorizerParams {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private Integer id;		//ID，自增长
	@Column(name="authorizer_appid")
	private String authorizerAppId;			//授权方appid
	@Column(name="authorizer_appsecret")
	private String authorizerAppSecret;		//授权方appsecret
	@Column(name="authorizer_access_token")
	private String authorizerAccessToken;	//授权方接口调用凭据（在授权的公众号具备API权限时，才有此返回值），也简称为令牌
	@Column(name="token_fresh_time")
	private String tokenFreshTime;		    //Token刷新时间
	@Column(name="token_fresh_rate")
	private Integer tokenFreshRate;    			//Token刷新频率
	@Column(name="authorizer_refresh_token")
	private String authorizerRefreshToken;	//接口调用凭据刷新令牌
	@Column(name="func_info")
	private String funcInfo;						//公众号授权给开发者的权限集列表
	@Column(name="sid")
	private String sid;							//自定义授权码，服务号需要拿到此授权码才能进行授权托管，也是公众号自定义唯一标示，用以区分用户访问的那个公众号
	@Column(name="authorizer_appname")
	private String authorizerAppName;		//授权方名称（服务号名称）
	@Column(name="authorizer_status")
	private int authorizerStatus = 1;			//授权状态 1-未授权，2-已授权
	@Column(name="company_id")
	private String companyId;//公司id
	@Column(name="company_Name")
	private String companyName;//公司名称
	@Column(name="dept_code")
	private String deptCode;						//店铺编码
	@Column(name="dept_name")
	private String deptName;				//店铺名称
	@Column(name="dept_telephone")
	private String deptTelephone;			//店铺电话
	@Column(name="linkman")
	private String linkman;					//联系人
	@Column(name="telephone")
	private String telephone;				//联系人电话
	@Column(name="province")
	private String province;					//省份
	@Column(name="city")
	private String city;						//城市
	@Column(name="area")
	private String area;						//区域
	@Column(name="address")
	private String address;					//详细地址
	@Column(name="latitude_longitude")
	private String latitudeLongitude;		//经纬度
	@Column(name="msg_signature")
	private String msgSignature;			//短信签名
	@Column(name="create_by")
	private String createBy;					//创建人
	@Column(name="update_by")
	private String updateBy;					//修改人
	@Column(name="create_time")
	private String createTime;//创建时间
	@Column(name="update_time")
	private String updateTime;//修改时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthorizerAppId() {
		return authorizerAppId;
	}
	public void setAuthorizerAppId(String authorizerAppId) {
		this.authorizerAppId = authorizerAppId;
	}
	public String getAuthorizerAppSecret() {
		return authorizerAppSecret;
	}
	public void setAuthorizerAppSecret(String authorizerAppSecret) {
		this.authorizerAppSecret = authorizerAppSecret;
	}
	public String getAuthorizerAccessToken() {
		return authorizerAccessToken;
	}
	public void setAuthorizerAccessToken(String authorizerAccessToken) {
		this.authorizerAccessToken = authorizerAccessToken;
	}
	public String getTokenFreshTime() {
		return tokenFreshTime;
	}
	public void setTokenFreshTime(String tokenFreshTime) {
		this.tokenFreshTime = tokenFreshTime;
	}
	public Integer getTokenFreshRate() {
		return tokenFreshRate;
	}
	public void setTokenFreshRate(Integer tokenFreshRate) {
		this.tokenFreshRate = tokenFreshRate;
	}
	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}
	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}
	public String getFuncInfo() {
		return funcInfo;
	}
	public void setFuncInfo(String funcInfo) {
		this.funcInfo = funcInfo;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getAuthorizerAppName() {
		return authorizerAppName;
	}
	public void setAuthorizerAppName(String authorizerAppName) {
		this.authorizerAppName = authorizerAppName;
	}
	public int getAuthorizerStatus() {
		return authorizerStatus;
	}
	public void setAuthorizerStatus(int authorizerStatus) {
		this.authorizerStatus = authorizerStatus;
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
	public String getDeptTelephone() {
		return deptTelephone;
	}
	public void setDeptTelephone(String deptTelephone) {
		this.deptTelephone = deptTelephone;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLatitudeLongitude() {
		return latitudeLongitude;
	}
	public void setLatitudeLongitude(String latitudeLongitude) {
		this.latitudeLongitude = latitudeLongitude;
	}
	public String getMsgSignature() {
		return msgSignature;
	}
	public void setMsgSignature(String msgSignature) {
		this.msgSignature = msgSignature;
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
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
