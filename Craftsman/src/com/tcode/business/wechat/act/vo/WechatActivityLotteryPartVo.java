package com.tcode.business.wechat.act.vo;

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
public class WechatActivityLotteryPartVo {
	
	private Integer id;		//ID，自增长
	private String activityCode; 			//活动编码
	private String redeem_code;				//兑奖码
	private Integer redeemStatus;				//兑奖状态 0-未兑奖，1-已兑奖
	private String prizeLevel;			//奖品等级 0-特等奖，1-一等奖，2-二等奖，3，4...
	private String lotteryTime;				//抽奖时间
	private String redeemTime;				//兑奖时间
	private String openId; 				//openId
	private String appId; 				//appId
	private String companyId;//公司id
	private String companyName;//公司名称
	private String deptCode;						//店铺编码
	private String deptName;				//店铺名称
	private String createBy;					//创建人
	private String updateBy;					//修改人
	private String createTime;//创建时间
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
	public String getRedeem_code() {
		return redeem_code;
	}
	public void setRedeem_code(String redeem_code) {
		this.redeem_code = redeem_code;
	}
	public Integer getRedeemStatus() {
		return redeemStatus;
	}
	public void setRedeemStatus(Integer redeemStatus) {
		this.redeemStatus = redeemStatus;
	}
	public String getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(String prizeLevel) {
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

	
}
