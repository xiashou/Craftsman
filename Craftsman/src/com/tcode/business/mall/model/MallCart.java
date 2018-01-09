package com.tcode.business.mall.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import com.tcode.business.mall.model.joint.GoodsMemID;

@Entity
@Scope("prototype")
@IdClass(GoodsMemID.class)
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "mall_cart")
public class MallCart {

	private String goodsId;
	private Integer memId;
	private Integer sendMode;
	private Double number;
	private String createTime;
	
	private MallGoods goods;
	private String modeName;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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
	public Integer getSendMode() {
		return sendMode;
	}
	public void setSendMode(Integer sendMode) {
		this.sendMode = sendMode;
	}
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Transient
	public MallGoods getGoods() {
		return goods;
	}
	public void setGoods(MallGoods goods) {
		this.goods = goods;
	}
	@Transient
	public String getModeName() {
		return modeName;
	}
	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
	
}
