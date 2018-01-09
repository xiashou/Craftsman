package com.tcode.business.goods.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "god_hour")
public class GoodsHour {

	private String id;
	private String deptCode;
	private Integer typeId;
	private String name;
	private String shorthand;
	private Double price;
	private Integer remindId;
	
	private String typeName;
	private String suitModel;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")     
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@Column(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "dept_code")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	@Column(name = "type_id")
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "price")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Column(name = "remind_id")
	public Integer getRemindId() {
		return remindId;
	}
	public void setRemindId(Integer remindId) {
		this.remindId = remindId;
	}
	public String getShorthand() {
		return shorthand;
	}
	public void setShorthand(String shorthand) {
		this.shorthand = shorthand;
	}
	
	
	@Transient
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Transient
	public String getSuitModel() {
		return suitModel;
	}
	public void setSuitModel(String suitModel) {
		this.suitModel = suitModel;
	}
}
