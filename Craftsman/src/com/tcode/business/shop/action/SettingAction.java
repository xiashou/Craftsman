package com.tcode.business.shop.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.shop.model.Setting;
import com.tcode.business.shop.service.SettingService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("settingAction")
public class SettingAction extends BaseAction {

	private static final long serialVersionUID = 8206852408865695361L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SettingService settingService;
	
	private Setting setting;
	
	private File upload;
	private String uploadFileName;
	
	
	/**
	 * 查询部门所有设置
	 * @return
	 */
	public String querySettingByDept() {
		try {
			setting = settingService.getById(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加参数配置
	 * @return
	 */
	public String insertSetting() {
		try {
			if(!Utils.isEmpty(setting)){
				setting.setDeptCode(this.getDept().getDeptCode());
				if (!Utils.isEmpty(upload)) {
		        	String realpath = ServletActionContext.getServletContext().getRealPath("/upload/store/logo");
		        	String suffix;
		        	if(uploadFileName != null && !"".equals(uploadFileName) && uploadFileName.indexOf(".") > 0)
						suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
					else
						suffix = "";
		        	if("jpeg".equals(suffix) || "jpg".equals(suffix) || "png".equals(suffix) || "gif".equals(suffix) || "bmp".equals(suffix)){
		        		uploadFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
		        		File savefile = new File(new File(realpath), uploadFileName);
		        		if (!savefile.getParentFile().exists())
		        			savefile.getParentFile().mkdirs();
		        		FileUtils.copyFile(upload, savefile);
		        		this.setResult(true, "上传成功！");
		        	} else
		        		this.setResult(false, "请上传正确的图片格式！");
		        	setting.setLogo(uploadFileName);
		        }
				settingService.insert(setting);
				this.setResult(true, "添加成功！");
			}
		} catch (Exception e){
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改参数配置
	 * @return
	 */
	public String updateSetting() {
		try {
			if(!Utils.isEmpty(setting)){
				setting.setDeptCode(this.getDept().getDeptCode());
				if (!Utils.isEmpty(upload)) {
		        	String realpath = ServletActionContext.getServletContext().getRealPath("/upload/store/logo");
		        	String suffix;
		        	if(uploadFileName != null && !"".equals(uploadFileName) && uploadFileName.indexOf(".") > 0)
						suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
					else
						suffix = "";
		        	if("jpeg".equals(suffix) || "jpg".equals(suffix) || "png".equals(suffix) || "gif".equals(suffix) || "bmp".equals(suffix)){
		        		uploadFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
		        		File savefile = new File(new File(realpath), uploadFileName);
		        		if (!savefile.getParentFile().exists())
		        			savefile.getParentFile().mkdirs();
		        		FileUtils.copyFile(upload, savefile);
		        		this.setResult(true, "上传成功！");
		        	} else
		        		this.setResult(false, "请上传正确的图片格式！");
		        	setting.setLogo(uploadFileName);
		        }
				settingService.update(setting);
				this.setResult(true, "修改成功！");
			}
		} catch (Exception e){
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除参数配置
	 * @return
	 */
	public String deleteSetting() {
		try {
			if(!Utils.isEmpty(setting) && !Utils.isEmpty(setting.getDeptCode())){
				settingService.delete(setting);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	

	public SettingService getSettingService() {
		return settingService;
	}
	@Resource
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	public Setting getSetting() {
		return setting;
	}
	public void setSetting(Setting setting) {
		this.setting = setting;
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

}
