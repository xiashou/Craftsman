package com.tcode.business.inte.jxcs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 维修企业信息注册备案
 * @author TSM
 *
 */
@Entity
@Scope("prototype")
@Table(name = "inte_jx_register")
public class Register {
	
	@JSONField(serialize=false)
	private String deptCode;				
	
	private String cmpyCompanycode;			//汽车维修企业费用唯一标识
	private String companycode;				//部级返回的企业级编码
	private String companyloginname;		//维修企业登录用户名
	private String companypassword;			//维修企业登录密码
	private String name;					//企业名称
	private String industryid;				//企业经营许可证号
	private String orgnumber;				//组织机构代码
	private String areaid;					//行政区划
	private String address;					//企业地址（100）
	private String linkzip;					//邮政编码
	private String emails;					//企业邮箱
	private String telphone;				//电话号码
	private String businesstype;			//企业经济类型
	private String levels;					//企业经营业务类别
	private String linkman;					//联系人姓名
	private String linktel;					//联系人联系方式
	private String legalname;				//负责人姓名
	private String legaltel;				//负责人联系方式
	private String businessrange;			//企业经营范围
	private String certificatefirsttime;	//营业执照发证日期
	private String certificatestarttime;	//营业期限开始时间
	private String certificateendtime;		//营业期限结束时间
	private String operatestate;			//企业经营状态
	private String summary;					//企业概述
	private String majorBrand;				//主修品牌
	private String warranty;				//质保信息
	private String service;					//优势服务
	private String promotion;				//近期活动
	private String superCompany;			//上级企业名称
	private String superIndustryId;			//上级企业经营许可证号
	private String industryOpenDate;		//道路经营许可证其实日期
	private String industryDieDate;			//道路经营许可证结束日期
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getCmpyCompanycode() {
		return cmpyCompanycode;
	}
	public void setCmpyCompanycode(String cmpyCompanycode) {
		this.cmpyCompanycode = cmpyCompanycode;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getCompanyloginname() {
		return companyloginname;
	}
	public void setCompanyloginname(String companyloginname) {
		this.companyloginname = companyloginname;
	}
	public String getCompanypassword() {
		return companypassword;
	}
	public void setCompanypassword(String companypassword) {
		this.companypassword = companypassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIndustryid() {
		return industryid;
	}
	public void setIndustryid(String industryid) {
		this.industryid = industryid;
	}
	public String getOrgnumber() {
		return orgnumber;
	}
	public void setOrgnumber(String orgnumber) {
		this.orgnumber = orgnumber;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLinkzip() {
		return linkzip;
	}
	public void setLinkzip(String linkzip) {
		this.linkzip = linkzip;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinktel() {
		return linktel;
	}
	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}
	public String getLegalname() {
		return legalname;
	}
	public void setLegalname(String legalname) {
		this.legalname = legalname;
	}
	public String getLegaltel() {
		return legaltel;
	}
	public void setLegaltel(String legaltel) {
		this.legaltel = legaltel;
	}
	public String getBusinessrange() {
		return businessrange;
	}
	public void setBusinessrange(String businessrange) {
		this.businessrange = businessrange;
	}
	public String getCertificatefirsttime() {
		return certificatefirsttime;
	}
	public void setCertificatefirsttime(String certificatefirsttime) {
		this.certificatefirsttime = certificatefirsttime;
	}
	public String getCertificatestarttime() {
		return certificatestarttime;
	}
	public void setCertificatestarttime(String certificatestarttime) {
		this.certificatestarttime = certificatestarttime;
	}
	public String getCertificateendtime() {
		return certificateendtime;
	}
	public void setCertificateendtime(String certificateendtime) {
		this.certificateendtime = certificateendtime;
	}
	public String getOperatestate() {
		return operatestate;
	}
	public void setOperatestate(String operatestate) {
		this.operatestate = operatestate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getMajorBrand() {
		return majorBrand;
	}
	public void setMajorBrand(String majorBrand) {
		this.majorBrand = majorBrand;
	}
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getSuperCompany() {
		return superCompany;
	}
	public void setSuperCompany(String superCompany) {
		this.superCompany = superCompany;
	}
	public String getSuperIndustryId() {
		return superIndustryId;
	}
	public void setSuperIndustryId(String superIndustryId) {
		this.superIndustryId = superIndustryId;
	}
	public String getIndustryOpenDate() {
		return industryOpenDate;
	}
	public void setIndustryOpenDate(String industryOpenDate) {
		this.industryOpenDate = industryOpenDate;
	}
	public String getIndustryDieDate() {
		return industryDieDate;
	}
	public void setIndustryDieDate(String industryDieDate) {
		this.industryDieDate = industryDieDate;
	}
	
}
