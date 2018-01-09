package com.tcode.business.msg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 短信计数计费
 * @author supeng
 *
 */
@Entity
@Table(name="msg_charging")
public class MsgCharging {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private int id;		//ID，自增长
	@Column(name="dept_code")
	private String deptCode; 				//门店代码
	@Column(name="send_num")
	private int sendNum;							//已发送条数
	@Column(name="remaining_num")
	private int remainingNum;						//剩余条数
	@Column(name="create_by")
	private String createBy;					//创建人
	@Column(name="update_by")
	private String updateBy;					//修改人
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
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public int getSendNum() {
		return sendNum;
	}
	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}
	public int getRemainingNum() {
		return remainingNum;
	}
	public void setRemainingNum(int remainingNum) {
		this.remainingNum = remainingNum;
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

}
