package com.tcode.business.finance.model;

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
@Table(name = "fico_expenditure")
public class Expenditure {

	private Integer id;
	private String deptCode;
	private Integer exptypeId;
	private Integer assetsId;
	private Double price;
	private String remark;
	private String creator;
	private String createTime;
	
	private String startDate;
	private String endDate;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Column(name = "exptype_id")
	public Integer getExptypeId() {
		return exptypeId;
	}
	public void setExptypeId(Integer exptypeId) {
		this.exptypeId = exptypeId;
	}
	@Column(name = "assets_id")
	public Integer getAssetsId() {
		return assetsId;
	}
	public void setAssetsId(Integer assetsId) {
		this.assetsId = assetsId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	@Transient
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Transient
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
