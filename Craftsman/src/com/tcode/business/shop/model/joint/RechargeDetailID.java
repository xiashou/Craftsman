package com.tcode.business.shop.model.joint;

import java.io.Serializable;

import javax.persistence.Column;

public class RechargeDetailID implements Serializable {

	private static final long serialVersionUID = -4778332873900267262L;
	
	private Integer rechargeId;
	private Integer itemNo;
	
	@Column(name = "recharge_id")
	public Integer getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(Integer rechargeId) {
		this.rechargeId = rechargeId;
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
		result = prime * result
				+ ((rechargeId == null) ? 0 : rechargeId.hashCode());
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
		RechargeDetailID other = (RechargeDetailID) obj;
		if (itemNo == null) {
			if (other.itemNo != null)
				return false;
		} else if (!itemNo.equals(other.itemNo))
			return false;
		if (rechargeId == null) {
			if (other.rechargeId != null)
				return false;
		} else if (!rechargeId.equals(other.rechargeId))
			return false;
		return true;
	}
	
}
