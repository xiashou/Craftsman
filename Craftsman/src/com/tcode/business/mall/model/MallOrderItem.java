package com.tcode.business.mall.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@Table(name = "mall_order_item")
public class MallOrderItem {

	private Integer itemId;
	private String orderId;
	private String goodsId;
	private String goodsName;
	private Double oprice;
	private Double aprice;
	private Integer number;
	private Integer sendMode;
	
	private String pictures;
	
	@Id
	@GeneratedValue
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getSendMode() {
		return sendMode;
	}
	public void setSendMode(Integer sendMode) {
		this.sendMode = sendMode;
	}
	
	
	@Transient
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public MallOrderItem() {
		super();
	}
	public MallOrderItem(Integer itemId, String orderId, String goodsId, String goodsName, 
			Double oprice, Double aprice, Integer number, Integer sendMode, String pictures) {
		super();
		this.itemId = itemId;
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.oprice = oprice;
		this.aprice = aprice;
		this.number = number;
		this.sendMode = sendMode;
		this.pictures = pictures;
	}
	
}
