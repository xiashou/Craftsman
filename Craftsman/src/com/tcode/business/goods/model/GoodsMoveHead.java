package com.tcode.business.goods.model;

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
@Table(name = "god_move_head")
public class GoodsMoveHead {
	
	private Integer id;
	private String deptOut;
	private String deptIn;
	private Double total;
	private Integer status;
	private String creator;
	private String createTime;
	private String updator;
	private String updateTime;
	
	private String deptOutName;
	private String deptInName;
	private String goodsId;
	private String goodsName;
	private Double number;
	private Double inPrice;
	private String dept;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "dept_out")
	public String getDeptOut() {
		return deptOut;
	}
	public void setDeptOut(String deptOut) {
		this.deptOut = deptOut;
	}
	@Column(name = "dept_in")
	public String getDeptIn() {
		return deptIn;
	}
	public void setDeptIn(String deptIn) {
		this.deptIn = deptIn;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	@Column(name = "update_time")
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
	
	@Transient
	public String getDeptOutName() {
		return deptOutName;
	}
	public void setDeptOutName(String deptOutName) {
		this.deptOutName = deptOutName;
	}
	@Transient
	public String getDeptInName() {
		return deptInName;
	}
	public void setDeptInName(String deptInName) {
		this.deptInName = deptInName;
	}
	@Transient
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	@Transient
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Transient
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	@Transient
	public Double getInPrice() {
		return inPrice;
	}
	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}
	@Transient
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	
}
