package com.tcode.system.model.param;

import java.util.List;

import com.tcode.system.model.SysMenu;

public class SysMenuParam {

	private String text;
	private Boolean checked;
	private String buttons;
	private List<SysMenu> children;

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
	
	
}
