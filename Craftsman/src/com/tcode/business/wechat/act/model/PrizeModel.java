package com.tcode.business.wechat.act.model;

public class PrizeModel {
	
	private int id;
	private double prizeRate; //中奖概率（%）
	private int prizeLevel; //奖品等级
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrizeRate() {
		return prizeRate;
	}
	public void setPrizeRate(double prizeRate) {
		this.prizeRate = prizeRate;
	}
	public int getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(int prizeLevel) {
		this.prizeLevel = prizeLevel;
	}

	
}
