package com.tcode.business.goods.model.joint;

import java.io.Serializable;

import javax.persistence.Column;

public class PackageDetailId implements Serializable {

	private static final long serialVersionUID = 1624442548277854927L;

	private String gpId;
	private Integer itemNo;
	
	
	@Column(name = "gp_id")
	public String getGpId() {
		return gpId;
	}
	public void setGpId(String gpId) {
		this.gpId = gpId;
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
		result = prime * result + ((gpId == null) ? 0 : gpId.hashCode());
		result = prime * result + ((itemNo == null) ? 0 : itemNo.hashCode());
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
		PackageDetailId other = (PackageDetailId) obj;
		if (gpId == null) {
			if (other.gpId != null)
				return false;
		} else if (!gpId.equals(other.gpId))
			return false;
		if (itemNo == null) {
			if (other.itemNo != null)
				return false;
		} else if (!itemNo.equals(other.itemNo))
			return false;
		return true;
	}
	
}
