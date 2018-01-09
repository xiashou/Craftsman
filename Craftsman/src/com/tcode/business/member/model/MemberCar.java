package com.tcode.business.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "mem_cars")
public class MemberCar {

	private Integer id;
	private Integer memberId;
	private String carShort;
	private String carCode;
	private String carNumber;
	private Integer carBrand;
	private String carSeries;
	private String carModel;
	private String carFrame;
	private String carEngine;
	private Integer carKilometers;//保养公里数
	private Integer carNextkilo;//下次保养公里数
	private String carInspection;//下次年检日期
	private String carMaintain;//下次保养日期
	private String carInsurance;//下次保险日期
	private String carInsuCompany;
	private String carIllegal;
	private String carMobile;
	private Integer series;
	private Integer model;
	
	private String showName;
	private String brandName;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "member_id")
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	@Column(name = "car_short")
	public String getCarShort() {
		return carShort;
	}
	public void setCarShort(String carShort) {
		this.carShort = carShort;
	}
	@Column(name = "car_code")
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	@Column(name = "car_number")
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	@Column(name = "car_brand")
	public Integer getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(Integer carBrand) {
		this.carBrand = carBrand;
	}
	@Column(name = "car_series")
	public String getCarSeries() {
		return carSeries;
	}
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}
	@Column(name = "car_model")
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	@Column(name = "car_frame")
	public String getCarFrame() {
		return carFrame;
	}
	public void setCarFrame(String carFrame) {
		this.carFrame = carFrame;
	}
	@Column(name = "car_engine")
	public String getCarEngine() {
		return carEngine;
	}
	public void setCarEngine(String carEngine) {
		this.carEngine = carEngine;
	}
	@Column(name = "car_kilometers")
	public Integer getCarKilometers() {
		return carKilometers;
	}
	public void setCarKilometers(Integer carKilometers) {
		this.carKilometers = carKilometers;
	}
	@Column(name = "car_nextkilo")
	public Integer getCarNextkilo() {
		return carNextkilo;
	}
	public void setCarNextkilo(Integer carNextkilo) {
		this.carNextkilo = carNextkilo;
	}
	@Column(name = "car_inspection")
	public String getCarInspection() {
		return carInspection;
	}
	public void setCarInspection(String carInspection) {
		this.carInspection = carInspection;
	}
	@Column(name = "car_maintain")
	public String getCarMaintain() {
		return carMaintain;
	}
	public void setCarMaintain(String carMaintain) {
		this.carMaintain = carMaintain;
	}
	@Column(name = "car_insurance")
	public String getCarInsurance() {
		return carInsurance;
	}
	public void setCarInsurance(String carInsurance) {
		this.carInsurance = carInsurance;
	}
	@Column(name = "car_insu_company")
	public String getCarInsuCompany() {
		return carInsuCompany;
	}
	public void setCarInsuCompany(String carInsuCompany) {
		this.carInsuCompany = carInsuCompany;
	}
	@Column(name = "car_illegal")
	public String getCarIllegal() {
		return carIllegal;
	}
	public void setCarIllegal(String carIllegal) {
		this.carIllegal = carIllegal;
	}
	@Column(name = "car_mobile")
	public String getCarMobile() {
		return carMobile;
	}
	public void setCarMobile(String carMobile) {
		this.carMobile = carMobile;
	}
	public Integer getSeries() {
		return series;
	}
	public void setSeries(Integer series) {
		this.series = series;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	
	
	@Transient
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	@Transient
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
}
