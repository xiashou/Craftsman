package com.tcode.business.finance.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import com.tcode.business.finance.model.joint.PayableID;

@Entity
@Scope("prototype")
@IdClass(PayableID.class)
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "fico_payable")
public class Payable {

	private String deptCode;
	private Integer supplierId;
	private Double payable;
	private Double repayment;
	private Integer status;
	private String lastUpdate;
	
	private String name;
	private String mobile;
	private String contact;
	private Double residual;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public Double getPayable() {
		return payable;
	}
	public void setPayable(Double payable) {
		this.payable = payable;
	}
	public Double getRepayment() {
		return repayment;
	}
	public void setRepayment(Double repayment) {
		this.repayment = repayment;
	}
	@Column(name = "last_update")
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Payable() {
		super();
	}
	public Payable(String deptCode, Integer supplierId, Double payable) {
		super();
		this.deptCode = deptCode;
		this.supplierId = supplierId;
		this.payable = payable;
		this.status = 0;
		this.lastUpdate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	}
	
	
	
	
	@Transient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Transient
	public Double getResidual() {
		return residual;
	}
	public void setResidual(Double residual) {
		this.residual = residual;
	}
	@Transient
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
}
