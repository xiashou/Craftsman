package com.tcode.business.wechat.act.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 抽奖活动-次
 * @author supeng
 *
 */
public class WechatActivityLotteryItemVo {
	
	private Integer id;		//ID，自增长
	private String activityCode; 			//活动编码
	private Integer prizeLevel;			//奖品等级 0-特等奖，1-一等奖，2-二等奖，3，4...
	private String levelName;			//自定义奖项名称（如特等奖、幸运奖）
	private Integer prizeNum;				//奖品总数量
	private String prizeDesc;			//奖品描述
	private Integer prizeType;				//奖品类型 0-实物奖品，1-积分，2-套餐...
	private Integer prizeWin;				//中奖获得数量（如中一次获得多少积分）
	private Integer prizeRate;				//中奖概率（%）
	private String prizeCode;			//奖品编码（虚拟奖品 如套餐，优惠券等）
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
	public Integer getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(Integer prizeLevel) {
		this.prizeLevel = prizeLevel;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getPrizeNum() {
		return prizeNum;
	}
	public void setPrizeNum(Integer prizeNum) {
		this.prizeNum = prizeNum;
	}
	public String getPrizeDesc() {
		return prizeDesc;
	}
	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}
	public Integer getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}
	public Integer getPrizeWin() {
		return prizeWin;
	}
	public void setPrizeWin(Integer prizeWin) {
		this.prizeWin = prizeWin;
	}
	public Integer getPrizeRate() {
		return prizeRate;
	}
	public void setPrizeRate(Integer prizeRate) {
		this.prizeRate = prizeRate;
	}
	public String getPrizeCode() {
		return prizeCode;
	}
	public void setPrizeCode(String prizeCode) {
		this.prizeCode = prizeCode;
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
