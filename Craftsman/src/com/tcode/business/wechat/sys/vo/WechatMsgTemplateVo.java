package com.tcode.business.wechat.sys.vo;

public class WechatMsgTemplateVo {
	
	private int id;		//ID，自增长
	private String templateNo;			//模板编号
	private String templateTitle;		//模板标题
	private String templateId;				//模板ID
	private String deptCode;						//店铺编码
	private String deptName;				//店铺名称
	private String createBy;					//创建人
	private String updateBy;					//修改人
	private String createTime;//创建时间
	private String updateTime;//修改时间
	private int sendRate;//发送频率
	private int sendBDays;//开始发送时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	public String getTemplateTitle() {
		return templateTitle;
	}
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getSendRate() {
		return sendRate;
	}
	public void setSendRate(int sendRate) {
		this.sendRate = sendRate;
	}
	public int getSendBDays() {
		return sendBDays;
	}
	public void setSendBDays(int sendBDays) {
		this.sendBDays = sendBDays;
	}

}
