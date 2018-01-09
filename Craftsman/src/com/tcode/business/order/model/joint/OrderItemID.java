package com.tcode.business.order.model.joint;

import java.io.Serializable;

import javax.persistence.Column;

public class OrderItemID implements Serializable {

	private static final long serialVersionUID = -7751324118345912626L;

	private String orderId;
	private Integer itemNo;
	
	@Column(name = "order_id")
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Column(name = "item_no")
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemNo == null) ? 0 : itemNo.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemID other = (OrderItemID) obj;
		if (itemNo == null) {
			if (other.itemNo != null)
				return false;
		} else if (!itemNo.equals(other.itemNo))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}
	
}
