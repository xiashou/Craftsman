package com.tcode.business.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "mem_grade")
public class MemberGrade {

	private Integer id;
	private Integer grade;
	private String deptCode;
	private String name;
	private Integer needPoint;
	private Double discount;
	private Double orderDiscount;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "need_point")
	public Integer getNeedPoint() {
		return needPoint;
	}
	public void setNeedPoint(Integer needPoint) {
		this.needPoint = needPoint;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	@Column(name = "order_discount")
	public Double getOrderDiscount() {
		return orderDiscount;
	}
	public void setOrderDiscount(Double orderDiscount) {
		this.orderDiscount = orderDiscount;
	}
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
}
