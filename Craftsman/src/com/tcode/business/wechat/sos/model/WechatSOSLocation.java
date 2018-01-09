package com.tcode.business.wechat.sos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wechat_sos_location")
public class WechatSOSLocation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;                    //ID，自增长   
	@Column(name="company_id")
	private String companyId;         //公司ID    
	@Column(name="company_name")
	private String companyName;       //公司名称   
	@Column(name="dept_code")
	private String deptCode;          //店铺编码    
	@Column(name="dept_name")
	private String deptName;          //店铺名称      
	@Column(name="vip_no")
	private String vipNo;
	@Column(name="openid")
	private String openid;             //openId     
	@Column(name="appid")
	private String appid;              //appId      
	@Column(name="latitude")
	private String latitude;           //纬度       
	@Column(name="longitude")
	private String longitude;          //经度        
	@Column(name="speed")
	private String speed;              //速度，以米/每秒计  
	@Column(name="accuracy")
	private String accuracy;           //位置精度       
	@Column(name="create_by")
	private String createBy;	       //创建人       
	@Column(name="update_by")
	private String updateBy;	       //修改人     
	@Column(name="create_time")
	private String createTime;        //创建时间    
	@Column(name="update_time")
	private String updateTime;        //修改时间    
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
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
	public String getVipNo() {
		return vipNo;
	}
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}

}
