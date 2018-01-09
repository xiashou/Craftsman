package com.tcode.business.order.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "order_head")
public class OrderHead {

	private String orderId;
	private String orderType;
	private String deptCode;
	private Integer memId;
	private String vipNo;
	private Integer carId;
	private String carNumber;
	private String saleDate;
	private Double oprice;
	private Double aprice;
	private Integer point;
	private Integer status;
	private Integer poffset;//抵现积分
	private Double pdeposit;//支付定金
	private Double pbalance;//支付余额
	private Double pcash;//支付现金
	private Double pcard;//支付刷卡
	private Double ptransfer;//支付转账
	private Double pwechat;//支付微信
	private Double palipay;//支付其他
	private Double pbill;//支付挂账
	private Integer printCount;
	private String inscompany;
	private String sender;
	private Double commission;
	private Integer mainten;//保养公里数
	private String estimate;//预计交车时间
	private String creator;
	private String createTime;
	private String remark;
	
	private String name;
	private String payType;
	private Double disprice;
	private Double cost;	//成本
	private Double profit;	//毛利
	private String mobile;
	private String startDate;
	private String endDate;
	private Double startPrice;
	private Double endPrice;
	private String deleteUser;
	private String deleteTime;
	private List<OrderItem> orderItemList;
	private String deptName;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@Column(name = "order_id")
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Column(name = "order_type")
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Column(name = "mem_id")
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
	@Column(name = "car_id")
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	@Column(name = "car_number")
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@Column(name = "sale_date")
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public Double getOprice() {
		return oprice;
	}
	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}
	public Double getAprice() {
		return aprice;
	}
	public void setAprice(Double aprice) {
		this.aprice = aprice;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPoffset() {
		return poffset;
	}
	public void setPoffset(Integer poffset) {
		this.poffset = poffset;
	}
	public Double getPdeposit() {
		return pdeposit;
	}
	public void setPdeposit(Double pdeposit) {
		this.pdeposit = pdeposit;
	}
	public Double getPbalance() {
		return pbalance;
	}
	public void setPbalance(Double pbalance) {
		this.pbalance = pbalance;
	}
	public Double getPcash() {
		return pcash;
	}
	public void setPcash(Double pcash) {
		this.pcash = pcash;
	}
	public Double getPcard() {
		return pcard;
	}
	public void setPcard(Double pcard) {
		this.pcard = pcard;
	}
	public Double getPtransfer() {
		return ptransfer;
	}
	public void setPtransfer(Double ptransfer) {
		this.ptransfer = ptransfer;
	}
	public Double getPwechat() {
		return pwechat;
	}
	public void setPwechat(Double pwechat) {
		this.pwechat = pwechat;
	}
	public Double getPalipay() {
		return palipay;
	}
	public void setPalipay(Double palipay) {
		this.palipay = palipay;
	}
	public Double getPbill() {
		return pbill;
	}
	public void setPbill(Double pbill) {
		this.pbill = pbill;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	@Column(name = "print_count")
	public Integer getPrintCount() {
		return printCount;
	}
	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}
	public String getInscompany() {
		return inscompany;
	}
	public void setInscompany(String inscompany) {
		this.inscompany = inscompany;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Integer getMainten() {
		return mainten;
	}
	public void setMainten(Integer mainten) {
		this.mainten = mainten;
	}
	public String getEstimate() {
		return estimate;
	}
	public void setEstimate(String estimate) {
		this.estimate = estimate;
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	@Transient
	public Double getDisprice() {
		return disprice;
	}
	public void setDisprice(Double disprice) {
		this.disprice = disprice;
	}
	@Transient
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	@Transient
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	@Transient
	public String getDeleteUser() {
		return deleteUser;
	}
	public void setDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}
	@Transient
	public String getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}
	@Transient
	public Double getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}
	@Transient
	public Double getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}
	@Transient
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	@Transient
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
