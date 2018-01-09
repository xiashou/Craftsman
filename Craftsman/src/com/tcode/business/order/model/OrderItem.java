package com.tcode.business.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import com.tcode.business.order.model.joint.OrderItemID;

@Entity
@Scope("prototype")
@IdClass(OrderItemID.class)
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "order_item")
public class OrderItem {

	private String orderId;
	private Integer itemNo;
	private Integer goodsType;
	private Integer typeId;
	private String goodsId;
	private String goodsName;
	private Double unitPrice;
	private Double number;
	private Double discount;
	private Double price;
	private String performer;
	private String seller;
	private String middleman;
	private Integer isDeduction;
	private String source;
	
	private String deptCode;
	private Integer orderType;
	private Integer memId;
	private String mobile;
	private String vipNo;
	private String name;
	private String sex;
	private Integer carId;
	private String carNumber;
	private String saleDate;
	private Integer status;
	private String remark;
	private String performerName;
	private String sellerName;
	private String startDate;
	private String endDate;
	private Double oprice;
	private Double aprice;
	private String typeName;
	private String spec;
	private String color;
	private Integer expire;
	private Integer mainten;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	@Column(name = "goods_type")
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	@Column(name = "type_id")
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	@Column(name = "goods_id")
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	@Column(name = "goods_name")
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column(name = "unit_price")
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPerformer() {
		return performer;
	}
	public void setPerformer(String performer) {
		this.performer = performer;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getMiddleman() {
		return middleman;
	}
	public void setMiddleman(String middleman) {
		this.middleman = middleman;
	}
	@Column(name = "is_deduction")
	public Integer getIsDeduction() {
		return isDeduction;
	}
	public void setIsDeduction(Integer isDeduction) {
		this.isDeduction = isDeduction;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public OrderItem() {
		super();
	}
	public OrderItem(String orderId, String goodsId, String goodsName,
			Double unitPrice, Double number, Double price, String source, String performer,
			String seller, String saleDate, Integer status) {
		super();
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.unitPrice = unitPrice;
		this.number = number;
		this.price = price;
		this.source = source;
		this.performer = performer;
		this.seller = seller;
		this.saleDate = saleDate;
		this.status = status;
	}
	
	
	
	
	
	
	
	
	@Transient
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Transient
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	@Transient
	public String getVipNo() {
		return vipNo;
	}
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
	@Transient
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	@Transient
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	@Transient
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@Transient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	@Transient
	public String getPerformerName() {
		return performerName;
	}
	public void setPerformerName(String performerName) {
		this.performerName = performerName;
	}
	@Transient
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Transient
	public Double getOprice() {
		return oprice;
	}
	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}
	@Transient
	public Double getAprice() {
		return aprice;
	}
	public void setAprice(Double aprice) {
		this.aprice = aprice;
	}
	@Transient
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Transient
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Transient
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	@Transient
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Transient
	public Integer getExpire() {
		return expire;
	}
	public void setExpire(Integer expire) {
		this.expire = expire;
	}
	@Transient
	public Integer getMainten() {
		return mainten;
	}
	public void setMainten(Integer mainten) {
		this.mainten = mainten;
	}
	
}
