package com.tcode.business.inte.jxcs.model;

/**
 * 维修记录基本信息
 * @author TSM
 *
 */
public class Baseinfo {

	private String cmpyCompanycode;			//企业编码
	private String statementno;				//结算清单编号
	private String carno;					//车牌号码
	private String carvin;					//车辆识别代号（vin）
	private String repairdate;				//送修日期
	private String repairmile;				//送修里程
	private String faultdescript;			//故障描述
	private String settledate;				//结算日期
	
	
	public String getCmpyCompanycode() {
		return cmpyCompanycode;
	}
	public void setCmpyCompanycode(String cmpyCompanycode) {
		this.cmpyCompanycode = cmpyCompanycode;
	}
	public String getStatementno() {
		return statementno;
	}
	public void setStatementno(String statementno) {
		this.statementno = statementno;
	}
	public String getCarno() {
		return carno;
	}
	public void setCarno(String carno) {
		this.carno = carno;
	}
	public String getCarvin() {
		return carvin;
	}
	public void setCarvin(String carvin) {
		this.carvin = carvin;
	}
	public String getRepairdate() {
		return repairdate;
	}
	public void setRepairdate(String repairdate) {
		this.repairdate = repairdate;
	}
	public String getRepairmile() {
		return repairmile;
	}
	public void setRepairmile(String repairmile) {
		this.repairmile = repairmile;
	}
	public String getFaultdescript() {
		return faultdescript;
	}
	public void setFaultdescript(String faultdescript) {
		this.faultdescript = faultdescript;
	}
	public String getSettledate() {
		return settledate;
	}
	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}
	
}
