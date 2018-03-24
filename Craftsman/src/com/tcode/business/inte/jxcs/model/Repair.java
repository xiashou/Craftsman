package com.tcode.business.inte.jxcs.model;

import java.util.List;

/**
 * 维修记录
 * @author TSM
 *
 */
public class Repair {

	private Baseinfo baseinfo;						//维修基本信息
	private List<Carparts> carpartslist;			//维修配件列表
	private List<Repairhours> repairhourslist;		//维修工时列表
	
	public Baseinfo getBaseinfo() {
		return baseinfo;
	}
	public void setBaseinfo(Baseinfo baseinfo) {
		this.baseinfo = baseinfo;
	}
	public List<Carparts> getCarpartslist() {
		return carpartslist;
	}
	public void setCarpartslist(List<Carparts> carpartslist) {
		this.carpartslist = carpartslist;
	}
	public List<Repairhours> getRepairhourslist() {
		return repairhourslist;
	}
	public void setRepairhourslist(List<Repairhours> repairhourslist) {
		this.repairhourslist = repairhourslist;
	}
	
}
