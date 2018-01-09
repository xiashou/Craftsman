package com.tcode.business.goods.model.joint;

import java.io.Serializable;

import javax.persistence.Column;

public class GoodsMoveID implements Serializable {

	private static final long serialVersionUID = -29994070878880965L;
	
	private Integer moveId;
	private Integer itemNo;
	
	@Column(name = "move_id")
	public Integer getMoveId() {
		return moveId;
	}
	public void setMoveId(Integer moveId) {
		this.moveId = moveId;
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
		result = prime * result + ((moveId == null) ? 0 : moveId.hashCode());
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
		GoodsMoveID other = (GoodsMoveID) obj;
		if (itemNo == null) {
			if (other.itemNo != null)
				return false;
		} else if (!itemNo.equals(other.itemNo))
			return false;
		if (moveId == null) {
			if (other.moveId != null)
				return false;
		} else if (!moveId.equals(other.moveId))
			return false;
		return true;
	}
	
	
}
