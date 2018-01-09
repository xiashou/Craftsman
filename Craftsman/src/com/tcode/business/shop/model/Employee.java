package com.tcode.business.shop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "shop_employee")
public class Employee {

	private Integer id;
	private String deptCode;
	private String name;
	private Boolean isGroup;
	private Integer groupLeader;
	private String groupMember;
	private String position;
	private String entryDate;
	private Boolean enable;
	private String lastDate;
	
	private Double comm1;
	private Double comm2;
	private Double comm3;
	private Double comm4;
	private Double comm5;
	private Double comm6;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "is_group")
	public Boolean getIsGroup() {
		return isGroup;
	}
	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}
	@Column(name = "group_leader")
	public Integer getGroupLeader() {
		return groupLeader;
	}
	public void setGroupLeader(Integer groupLeader) {
		this.groupLeader = groupLeader;
	}
	@Column(name = "group_member")
	public String getGroupMember() {
		return groupMember;
	}
	public void setGroupMember(String groupMember) {
		this.groupMember = groupMember;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Column(name = "entry_date")
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	@Column(name = "last_date")
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	
	
	
	
	
	@Transient
	public Double getComm1() {
		return comm1;
	}
	public void setComm1(Double comm1) {
		this.comm1 = comm1;
	}
	@Transient
	public Double getComm2() {
		return comm2;
	}
	public void setComm2(Double comm2) {
		this.comm2 = comm2;
	}
	@Transient
	public Double getComm3() {
		return comm3;
	}
	public void setComm3(Double comm3) {
		this.comm3 = comm3;
	}
	@Transient
	public Double getComm4() {
		return comm4;
	}
	public void setComm4(Double comm4) {
		this.comm4 = comm4;
	}
	@Transient
	public Double getComm5() {
		return comm5;
	}
	public void setComm5(Double comm5) {
		this.comm5 = comm5;
	}
	@Transient
	public Double getComm6() {
		return comm6;
	}
	public void setComm6(Double comm6) {
		this.comm6 = comm6;
	}
}
