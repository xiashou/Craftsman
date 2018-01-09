package com.tcode.business.member.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "mem_member")
public class Member {

	private Integer memId;
	private String vipNo;
	private String companyId;
	private String deptCode;
	private String name;
	private String mobile;
	private String sex;//1男 2女 0未知
	private Integer grade;
	private String appId;
	private String wechatNo;
	private String wehcatHead;
	private String wechatNick;
	private String source;
	private Double balance;
	private Integer point;
	private String birthday;
	private String picture;
	private String createTime;
	private String sales;
	private String remark;
	private String album;
	
	private Integer carId;
	private String carShort;
	private String carCode;
	private String carNumber;
	private Integer carBrand;
	private String gradeName;
	private String fullCarNumber;
	
	private Double startBalance;
	private Double endBalance;
	private Integer startPoint;
	private Integer endPoint;
	private String startBirth;
	private String endBirth;
	private String startCreate;
	private String endCreate;
	
	private List<MemberCar> carList;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	@Column(name = "vip_no")
	public String getVipNo() {
		return vipNo;
	}
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
	@Column(name = "company_id")
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	@Column(name = "wechat_no")
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	@Column(name = "wechat_head")
	public String getWehcatHead() {
		return wehcatHead;
	}
	public void setWehcatHead(String wehcatHead) {
		this.wehcatHead = wehcatHead;
	}
	@Column(name = "wechat_nick")
	public String getWechatNick() {
		return wechatNick;
	}
	public void setWechatNick(String wechatNick) {
		this.wechatNick = wechatNick;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
	
	
	
	
	
	@Transient
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	@Transient
	public String getCarShort() {
		return carShort;
	}
	public void setCarShort(String carShort) {
		this.carShort = carShort;
	}
	@Transient
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	@Transient
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@Transient
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	@Transient
	public String getFullCarNumber() {
		return fullCarNumber;
	}
	public void setFullCarNumber(String fullCarNumber) {
		this.fullCarNumber = fullCarNumber;
	}
	@Transient
	public List<MemberCar> getCarList() {
		return carList;
	}
	public void setCarList(List<MemberCar> carList) {
		this.carList = carList;
	}
	@Transient
	public Double getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}
	@Transient
	public Double getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}
	@Transient
	public Integer getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Integer startPoint) {
		this.startPoint = startPoint;
	}
	@Transient
	public Integer getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Integer endPoint) {
		this.endPoint = endPoint;
	}
	@Transient
	public String getStartBirth() {
		return startBirth;
	}
	public void setStartBirth(String startBirth) {
		this.startBirth = startBirth;
	}
	@Transient
	public String getEndBirth() {
		return endBirth;
	}
	public void setEndBirth(String endBirth) {
		this.endBirth = endBirth;
	}
	@Transient
	public String getStartCreate() {
		return startCreate;
	}
	public void setStartCreate(String startCreate) {
		this.startCreate = startCreate;
	}
	@Transient
	public String getEndCreate() {
		return endCreate;
	}
	public void setEndCreate(String endCreate) {
		this.endCreate = endCreate;
	}
	@Transient
	public Integer getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(Integer carBrand) {
		this.carBrand = carBrand;
	}
	
	
}
