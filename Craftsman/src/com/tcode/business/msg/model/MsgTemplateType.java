package com.tcode.business.msg.model;
/**
 * 短信模板类型
 * @author supeng
 *
 */
public class MsgTemplateType {
	
	private int id; //主键
	private int templateTypeNo;//模板类型编码
	private String templateTypeName;//模板类型名称
	private int property;//模板性质1-系统自带，2-客户新增
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTemplateTypeNo() {
		return templateTypeNo;
	}
	public void setTemplateTypeNo(int templateTypeNo) {
		this.templateTypeNo = templateTypeNo;
	}
	public String getTemplateTypeName() {
		return templateTypeName;
	}
	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}
	public int getProperty() {
		return property;
	}
	public void setProperty(int property) {
		this.property = property;
	}
}
