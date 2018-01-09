package com.tcode.business.basic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@Table(name = "base_area_short")
public class BaseAreaShort {

	private Integer id;
	private String areaShort;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "area_short")
	public String getAreaShort() {
		return areaShort;
	}
	public void setAreaShort(String areaShort) {
		this.areaShort = areaShort;
	}
	
}
