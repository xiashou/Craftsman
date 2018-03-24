package com.tcode.business.inte.jxcs.model;

/**
 * 维修记录工时信息
 * @author TSM
 *
 */
public class Repairhours {

	private String repairname;		//维修项目
	private Integer repairhours;	//工时
	
	public String getRepairname() {
		return repairname;
	}
	public void setRepairname(String repairname) {
		this.repairname = repairname;
	}
	public Integer getRepairhours() {
		return repairhours;
	}
	public void setRepairhours(Integer repairhours) {
		this.repairhours = repairhours;
	}
	
}
