package com.tcode.business.wechat.act.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@Table(name = "act_signup")
public class ActSignup {

	private Integer id;
	private String appId;
	private String name;
	private String dateStart;
	private String dateEnd;
	private String address;
	private String contact;
	private Double price;
	private Integer number;
	private String organization;
	private String introduce;
	private Integer signNumber;
	private Integer signFicNumber;
	private Integer readNumber;
	private Integer readFicNumber;
	private Integer collNumber;
	private Integer collFicNumber;
	private String pictures;
	private String fields;
	private Integer status;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public Integer getSignNumber() {
		return signNumber;
	}
	public void setSignNumber(Integer signNumber) {
		this.signNumber = signNumber;
	}
	public Integer getSignFicNumber() {
		return signFicNumber;
	}
	public void setSignFicNumber(Integer signFicNumber) {
		this.signFicNumber = signFicNumber;
	}
	public Integer getReadNumber() {
		return readNumber;
	}
	public void setReadNumber(Integer readNumber) {
		this.readNumber = readNumber;
	}
	public Integer getReadFicNumber() {
		return readFicNumber;
	}
	public void setReadFicNumber(Integer readFicNumber) {
		this.readFicNumber = readFicNumber;
	}
	public Integer getCollNumber() {
		return collNumber;
	}
	public void setCollNumber(Integer collNumber) {
		this.collNumber = collNumber;
	}
	public Integer getCollFicNumber() {
		return collFicNumber;
	}
	public void setCollFicNumber(Integer collFicNumber) {
		this.collFicNumber = collFicNumber;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	
}
