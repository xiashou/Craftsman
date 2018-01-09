package com.tcode.system.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.xml.internal.fastinfoset.stax.events.Util;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Constant;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysImage;
import com.tcode.system.service.SysImageService;

@Scope("prototype")
@Component("sysImageAction")
public class SysImageAction extends BaseAction {

	private static final long serialVersionUID = -9195819496371471322L;
	private static Logger log = Logger.getLogger("D");
	
	private SysImageService sysImageService;

	private List<SysImage> imageList;
	
	private SysImage sysImage;
	private File upload;
	private String uploadFileName;
	private String picPath;
	private String mode;
	
	/**
	 * 图片上传
	 * @return
	 */
	public String upload() {
		try {
			String realpath = "";
			if(!Utils.isEmpty(mode) && "mall".equals(mode))
				realpath = ServletActionContext.getServletContext().getRealPath(Constant.MALL_DETAIL_IMG_PATH);
			else if(!Utils.isEmpty(mode) && "activity".equals(mode))
				realpath = ServletActionContext.getServletContext().getRealPath(Constant.WECHAT_ACT_IMG_PATH);
			else
				realpath = ServletActionContext.getServletContext().getRealPath(Constant.SYS_IMG_PATH);
	        
			String imageId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".")).toLowerCase();
			uploadFileName = imageId + suffix;
			
			File uploadFile = new File(realpath, uploadFileName);
			if (!uploadFile.getParentFile().exists())
				uploadFile.getParentFile().mkdirs();
			
			FileUtils.copyFile(upload, uploadFile);
			
			this.setSuccess(true);
		} catch (NullPointerException e) {
			this.setMsg("上传文件不能为空！");
			this.setSuccess(false);
		} catch (Exception e) {
			this.setMsg("上传失败！ 原因：" + e.getMessage());
			this.setSuccess(false);
		}
		return SUCCESS;
	}

	/**
	 * 图片查询
	 * @return
	 */
	public String querySysImagePage() {
		try {
			imageList = sysImageService.getSysImageList(mode);
		} catch(Exception e){
			log.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 图片删除
	 * @return
	 */
	public String deleteSysImage() {
		try {
			String realpath = "";
			if(!Utils.isEmpty(mode) && "mall".equals(mode))
				realpath = ServletActionContext.getServletContext().getRealPath(Constant.MALL_DETAIL_IMG_PATH);
			else
				realpath = ServletActionContext.getServletContext().getRealPath(Constant.SYS_IMG_PATH);
			if(!Util.isEmptyString(picPath)){
				File delFile = new File(realpath, picPath.substring(picPath.lastIndexOf("/") + 1));
				if (delFile.exists()){
					this.setSuccess(delFile.delete());
					this.setMsg("删除成功！");
				} else
					this.setMsg("删除失败！图片不存在！");
			}
		} catch (NullPointerException e) {
			this.setMsg("文件不能为空！");
			this.setSuccess(false);
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setMsg("删除失败！ 原因：" + Utils.getErrorMessage(e));
			this.setSuccess(false);
		}
		return SUCCESS;
	}
	
	
	
	public SysImageService getSysImageService() {
		return sysImageService;
	}
	@Resource
	public void setSysImageService(SysImageService sysImageService) {
		this.sysImageService = sysImageService;
	}
	public List<SysImage> getImageList() {
		return imageList;
	}
	public void setImageList(List<SysImage> imageList) {
		this.imageList = imageList;
	}
	public SysImage getSysImage() {
		return sysImage;
	}
	public void setSysImage(SysImage sysImage) {
		this.sysImage = sysImage;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}

}
