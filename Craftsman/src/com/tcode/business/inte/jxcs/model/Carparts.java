package com.tcode.business.inte.jxcs.model;

/**
 * 维修记录配件信息
 * @author TSM
 *
 */
public class Carparts {

	private String partsname;			//配件名称
	private String partsno;				//配件编码
	private Integer partsquantity;		//数量
	
	public String getPartsname() {
		return partsname;
	}
	public void setPartsname(String partsname) {
		this.partsname = partsname;
	}
	public String getPartsno() {
		return partsno;
	}
	public void setPartsno(String partsno) {
		this.partsno = partsno;
	}
	public Integer getPartsquantity() {
		return partsquantity;
	}
	public void setPartsquantity(Integer partsquantity) {
		this.partsquantity = partsquantity;
	}
	
}
