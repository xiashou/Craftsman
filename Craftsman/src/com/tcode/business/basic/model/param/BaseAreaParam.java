package com.tcode.business.basic.model.param;

import java.util.List;

import com.tcode.business.basic.model.BaseArea;

public class BaseAreaParam {

	private String id;
	private String text;
	private Boolean leaf;
	private Boolean expanded;
	private List<BaseArea> children;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	public List<BaseArea> getChildren() {
		return children;
	}
	public void setChildren(List<BaseArea> children) {
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
