package com.tcode.business.goods.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "god_check_record")
public class GoodsCheckRecord {

	private Integer id;
	private String deptCode;
	private String goodsId;
	private Double oldNumber;
	private Double newNumber;
	private String creator;
	private String createTime;
	
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
	@Column(name = "goods_id")
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	@Column(name = "old_number")
	public Double getOldNumber() {
		return oldNumber;
	}
	public void setOldNumber(Double oldNumber) {
		this.oldNumber = oldNumber;
	}
	@Column(name = "new_number")
	public Double getNewNumber() {
		return newNumber;
	}
	public void setNewNumber(Double newNumber) {
		this.newNumber = newNumber;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public GoodsCheckRecord() {
		super();
	}
	public GoodsCheckRecord(String deptCode, String goodsId,
			Double oldNumber, Double newNumber, String creator,
			String createTime) {
		super();
		this.deptCode = deptCode;
		this.goodsId = goodsId;
		this.oldNumber = oldNumber;
		this.newNumber = newNumber;
		this.creator = creator;
		this.createTime = createTime;
	}
	
}
