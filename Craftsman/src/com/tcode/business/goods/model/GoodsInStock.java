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
@Table(name = "god_instock")
public class GoodsInStock {

	private Integer id;
	private String deptCode;
	private String inNumber;
	private String goodsId;
	private String goodsName;
	private Double number;
	private Double inPrice;
	private Integer supplier;
	private Integer settlement;
	private String settdate;
	private String purchdate;
	private Integer creator;
	private String createTime;
	
	private String startDate;
	private String endDate;
	private Double stockNum;
	private String supplierName;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "goods_id")
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Column(name = "in_number")
	public String getInNumber() {
		return inNumber;
	}
	public void setInNumber(String inNumber) {
		this.inNumber = inNumber;
	}
	@Column(name = "goods_name")
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	@Column(name = "in_price")
	public Double getInPrice() {
		return inPrice;
	}
	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}
	public Integer getSupplier() {
		return supplier;
	}
	public void setSupplier(Integer supplier) {
		this.supplier = supplier;
	}
	public Integer getSettlement() {
		return settlement;
	}
	public void setSettlement(Integer settlement) {
		this.settlement = settlement;
	}
	public String getSettdate() {
		return settdate;
	}
	public void setSettdate(String settdate) {
		this.settdate = settdate;
	}
	public String getPurchdate() {
		return purchdate;
	}
	public void setPurchdate(String purchdate) {
		this.purchdate = purchdate;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
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
	@Transient
	public Double getStockNum() {
		return stockNum;
	}
	public void setStockNum(Double stockNum) {
		this.stockNum = stockNum;
	}
	@Transient
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
	
}
