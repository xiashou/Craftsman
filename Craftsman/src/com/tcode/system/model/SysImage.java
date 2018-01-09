package com.tcode.system.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.context.annotation.Scope;

@Entity
@Scope("prototype")
public class SysImage {
	
	private String imgName;
	private String imgPath;
	
	@Id
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
