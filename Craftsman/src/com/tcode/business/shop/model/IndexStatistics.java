package com.tcode.business.shop.model;

public class IndexStatistics {

	private Integer id;
	private String text;
	private String value;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public IndexStatistics() {
		super();
	}
	public IndexStatistics(Integer id, String text, String value) {
		super();
		this.id = id;
		this.text = text;
		this.value = value;
	}
	
	
}
