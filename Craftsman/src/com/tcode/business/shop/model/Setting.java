package com.tcode.business.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "shop_setting")
public class Setting {

	private String deptCode;	//门店代码，主键
	private String shopName;
	private String logo;
	private String phone;
	private String address;
	private String shopRemark;
	private Double xlocation;
	private Double ylocation;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Column(name = "shop_name")
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "shop_remark")
	public String getShopRemark() {
		return shopRemark;
	}
	public void setShopRemark(String shopRemark) {
		this.shopRemark = shopRemark;
	}
	public Double getXlocation() {
		return xlocation;
	}
	public void setXlocation(Double xlocation) {
		this.xlocation = xlocation;
	}
	public Double getYlocation() {
		return ylocation;
	}
	public void setYlocation(Double ylocation) {
		this.ylocation = ylocation;
	}
	
}
