package com.tcode.business.wechat.act.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 抽奖活动-主
 * @author supeng
 *
 */
@Entity
@Table(name="wechat_activity_lottery_head")
public class WechatActivityLottery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private Integer id;		//ID，自增长
	@Column(name="activity_code")
	private String activityCode; 			//活动编码
	@Column(name="activity_title")
	private String activityTitle;			//活动标题
	@Column(name="activity_type")
	private Integer activityType;			//活动类型 0-砸金蛋 1-大转盘
	@Column(name="activity_status")
	private Integer activityStatus;		//活动状态 0-无效， 1-有效
	@Column(name="free_add_type")
	private Integer freeAddType;			//增加免费参与活动次数类型 0-无，1-消费，2-时间（如每天免费一次）
	@Column(name="free_add_num")
	private Integer freeAddNum;			//每次增加免费参与活动次数
	@Column(name="free_num")
	private Integer freeNum;			//剩余免费参与活动次数
	@Column(name="paid_consume_type")
	private Integer paidConsumeType;			//有偿消耗类型 0-无，1-积分，2-余额
	@Column(name="paid_consume_limit")
	private Integer paidConsumeLimit;			//每次有偿消耗额度
	@Column(name="b_date")
	private String bDate;				//活动开始日期
	@Column(name="e_date")
	private String eDate;				//活动结束日期
	@Column(name="activity_description")
	private String activityDescription;			//活动说明
	@Column(name="remark")
	private String remark;			//备注
	@Column(name="company_id")
	private String companyId;//公司id
	@Column(name="company_Name")
	private String companyName;//公司名称
	@Column(name="dept_code")
	private String deptCode;						//店铺编码
	@Column(name="dept_name")
	private String deptName;				//店铺名称
	@Column(name="create_by")
	private String createBy;					//创建人
	@Column(name="update_by")
	private String updateBy;					//修改人
	@Column(name="create_time")
	private String createTime;//创建时间
	@Column(name="update_time")
	private String updateTime;//修改时间
	@Transient
	private List<WechatActivityLotteryItem> wechatActivityLotteryItemList;
	@Transient
	private String levelNames;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public Integer getFreeAddType() {
		return freeAddType;
	}
	public void setFreeAddType(Integer freeAddType) {
		this.freeAddType = freeAddType;
	}
	public Integer getFreeAddNum() {
		return freeAddNum;
	}
	public void setFreeAddNum(Integer freeAddNum) {
		this.freeAddNum = freeAddNum;
	}
	public Integer getFreeNum() {
		return freeNum;
	}
	public void setFreeNum(Integer freeNum) {
		this.freeNum = freeNum;
	}
	public Integer getPaidConsumeType() {
		return paidConsumeType;
	}
	public void setPaidConsumeType(Integer paidConsumeType) {
		this.paidConsumeType = paidConsumeType;
	}
	public Integer getPaidConsumeLimit() {
		return paidConsumeLimit;
	}
	public void setPaidConsumeLimit(Integer paidConsumeLimit) {
		this.paidConsumeLimit = paidConsumeLimit;
	}
	public String getbDate() {
		return bDate;
	}
	public void setbDate(String bDate) {
		this.bDate = bDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public List<WechatActivityLotteryItem> getWechatActivityLotteryItemList() {
		return wechatActivityLotteryItemList;
	}
	public void setWechatActivityLotteryItemList(List<WechatActivityLotteryItem> wechatActivityLotteryItemList) {
		this.wechatActivityLotteryItemList = wechatActivityLotteryItemList;
	}
	public String getLevelNames() {
		return levelNames;
	}
	public void setLevelNames(String levelNames) {
		this.levelNames = levelNames;
	}
	
}
