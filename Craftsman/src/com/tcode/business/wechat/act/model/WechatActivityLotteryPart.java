package com.tcode.business.wechat.act.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 抽奖活动-参与人员
 * @author supeng
 *
 */
@Entity
@Table(name="wechat_activity_lottery_part")
public class WechatActivityLotteryPart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="id")
	private Integer id;		//ID，自增长
	@Column(name="activity_code")
	private String activityCode; 			//活动编码
	@Column(name="activity_title")
	private String activityTitle;			//活动标题
	@Column(name="redeem_code")
	private String redeemCode;				//兑奖码
	@Column(name="redeem_status")
	private Integer redeemStatus;				//兑奖状态 0-未兑奖，1-已兑奖
	@Column(name="prize_level")
	private Integer prizeLevel;			//奖品等级 0-特等奖，1-一等奖，2-二等奖，3，4...
	@Column(name="level_name")
	private String levelName;			//自定义奖项名称（如特等奖、幸运奖）
	@Column(name="prize_desc")
	private String prizeDesc;			//奖品描述
	@Column(name="prize_win")
	private Integer prizeWin;				//中奖获得数量
	@Column(name="lottery_time")
	private String lotteryTime;				//抽奖时间
	@Column(name="redeem_time")
	private String redeemTime;				//兑奖时间
	@Column(name="openid")
	private String openId; 				//openId
	@Column(name="appid")
	private String appId; 				//appId
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
	public String getRedeemCode() {
		return redeemCode;
	}
	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}
	public Integer getRedeemStatus() {
		return redeemStatus;
	}
	public void setRedeemStatus(Integer redeemStatus) {
		this.redeemStatus = redeemStatus;
	}
	public Integer getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(Integer prizeLevel) {
		this.prizeLevel = prizeLevel;
	}
	public String getLotteryTime() {
		return lotteryTime;
	}
	public void setLotteryTime(String lotteryTime) {
		this.lotteryTime = lotteryTime;
	}
	public String getRedeemTime() {
		return redeemTime;
	}
	public void setRedeemTime(String redeemTime) {
		this.redeemTime = redeemTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
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
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getPrizeDesc() {
		return prizeDesc;
	}
	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}
	public Integer getPrizeWin() {
		return prizeWin;
	}
	public void setPrizeWin(Integer prizeWin) {
		this.prizeWin = prizeWin;
	}

}
