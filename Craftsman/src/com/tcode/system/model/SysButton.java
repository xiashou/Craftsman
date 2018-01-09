package com.tcode.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@Table(name = "sys_button")
public class SysButton {

	private Integer btnId;
	private String btnKey;
	private String btnName;
	private String menuId;
	
	@Id
	@GeneratedValue
	@Column(name = "btn_id")
	public Integer getBtnId() {
		return btnId;
	}
	public void setBtnId(Integer btnId) {
		this.btnId = btnId;
	}
	@Column(name = "btn_key")
	public String getBtnKey() {
		return btnKey;
	}
	public void setBtnKey(String btnKey) {
		this.btnKey = btnKey;
	}
	@Column(name = "btn_name")
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	@Column(name = "menu_id")
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
