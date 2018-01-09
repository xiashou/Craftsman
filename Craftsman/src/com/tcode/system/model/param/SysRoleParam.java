package com.tcode.system.model.param;

import java.util.List;

import com.tcode.system.model.SysMenu;

public class SysRoleParam {
	
	private String text;
	private Boolean leaf;
	private Boolean checked;
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

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
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
	
	
}
