package com.tcode.business.msg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 短信模板
 * @author supeng
 *
 */
@Entity
@Table(name="msg_template")
public class MsgTemplate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;//主键
	@Column(name="template_type_no")
	private int templateTypeNo;//模板类型编码
	@Column(name="dept_code")
	private String deptCode;//门店代码
	@Column(name="content")
	private String content;//模板内容
	@Column(name="remaining_days")
	private int remainingDays;//剩余天数
	@Column(name="send_rate")
	private int sendRate;//发送频率
	@Column(name="create_time")
	private String createTime;//创建时间
	@Column(name="update_time")
	private String updateTime;//修改时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTemplateTypeNo() {
		return templateTypeNo;
	}
	public void setTemplateTypeNo(int templateTypeNo) {
		this.templateTypeNo = templateTypeNo;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRemainingDays() {
		return remainingDays;
	}
	public void setRemainingDays(int remainingDays) {
		this.remainingDays = remainingDays;
	}
	public int getSendRate() {
		return sendRate;
	}
	public void setSendRate(int sendRate) {
		this.sendRate = sendRate;
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
	
}
