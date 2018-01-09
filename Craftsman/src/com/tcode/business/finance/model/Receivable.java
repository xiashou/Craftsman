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

import com.tcode.business.finance.model.joint.ReceivableID;


@Entity
@Scope("prototype")
@IdClass(ReceivableID.class)
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "fico_receivable")
public class Receivable {

	private String deptCode;
	private Integer memId;
	private Integer carId;
	private Double billPrice;
	private Double repayment;
	private Integer status;
	private String lastUpdate;
	
	private String name;
	private String mobile;
	private String vipNo;
	private String carNumber;
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
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	@Column(name = "bill_price")
	public Double getBillPrice() {
		return billPrice;
	}
	public void setBillPrice(Double billPrice) {
		this.billPrice = billPrice;
	}
	public Double getRepayment() {
		return repayment;
	}
	public void setRepayment(Double repayment) {
		this.repayment = repayment;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "last_update")
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Receivable() {
		super();
	}
	public Receivable(String deptCode, Integer memId, Integer carId, Double billPrice) {
		super();
		this.deptCode = deptCode;
		this.memId = memId;
		this.carId = carId;
		this.billPrice = billPrice;
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
	public String getVipNo() {
		return vipNo;
	}
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
	@Transient
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@Transient
	public Double getResidual() {
		return residual;
	}
	public void setResidual(Double residual) {
		this.residual = residual;
	}
	
}
