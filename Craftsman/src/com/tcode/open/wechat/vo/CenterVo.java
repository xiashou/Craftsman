package com.tcode.open.wechat.vo;

import com.tcode.business.shop.model.Setting;

public class CenterVo {
	
	private String province;
	private String city;
	private Integer carId;//会员车辆ID
	private String carNumber;//车牌
	private String seriesName;//车型号
	private String wechatNick;
	private String sex;//1男 2女 0未知
	private String wehcatHead;
	private Double balance;
	private Integer point;
	private String inspectionRemainingTime;//年检剩余时间
	private String insuranceRemainingTime;//保险剩余时间
	private String maintainRemainingTime;//保养剩余时间
	private Setting settingInfo;//门店信息
	
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
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getWechatNick() {
		return wechatNick;
	}
	public void setWechatNick(String wechatNick) {
		this.wechatNick = wechatNick;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getWehcatHead() {
		return wehcatHead;
	}
	public void setWehcatHead(String wehcatHead) {
		this.wehcatHead = wehcatHead;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getInspectionRemainingTime() {
		return inspectionRemainingTime;
	}
	public void setInspectionRemainingTime(String inspectionRemainingTime) {
		this.inspectionRemainingTime = inspectionRemainingTime;
	}
	public String getMaintainRemainingTime() {
		return maintainRemainingTime;
	}
	public void setMaintainRemainingTime(String maintainRemainingTime) {
		this.maintainRemainingTime = maintainRemainingTime;
	}
	public String getInsuranceRemainingTime() {
		return insuranceRemainingTime;
	}
	public void setInsuranceRemainingTime(String insuranceRemainingTime) {
		this.insuranceRemainingTime = insuranceRemainingTime;
	}
	public Setting getSettingInfo() {
		return settingInfo;
	}
	public void setSettingInfo(Setting settingInfo) {
		this.settingInfo = settingInfo;
	}
	
}
