package com.tcode.business.mall.action;

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

import com.tcode.business.mall.model.MallBanner;
import com.tcode.business.mall.service.MallBannerService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallBannerAction")
public class MallBannerAction extends BaseAction {

	private static final long serialVersionUID = -8728453777879790033L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MallBannerService mallBannerService;
	
	private List<MallBanner> bannerList;
	
	private MallBanner banner;
	private File upload;
	private String uploadFileName;
	
	
	public String queryMallBannerList() {
		try {
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				bannerList = mallBannerService.getListByAppId(wechatApp.getAuthorizerAppId());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String insertMallBanner() {
		try {
			if(!Utils.isEmpty(banner)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					banner.setAppId(wechatApp.getAuthorizerAppId());
					if (!Utils.isEmpty(upload)) {
			        	String realpath = ServletActionContext.getServletContext().getRealPath("/upload/mall/banner");
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
			        	banner.setPicture(uploadFileName);
			        }
					mallBannerService.insert(banner);
					this.setResult(true, "添加成功！");
				} else
					this.setResult(true, "请先联系管理员配置公众号！");
			}
		} catch(Exception e) {
			this.setResult(true, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String updateMallBanner() {
		try {
			if(!Utils.isEmpty(banner) && !Utils.isEmpty(banner.getId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					banner.setAppId(wechatApp.getAuthorizerAppId());
					if (!Utils.isEmpty(upload)) {
			        	String realpath = ServletActionContext.getServletContext().getRealPath("/upload/mall/banner");
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
			        	banner.setPicture(uploadFileName);
			        }
					mallBannerService.update(banner);
					this.setResult(true, "修改成功！");
				} else
					this.setResult(true, "请先联系管理员配置公众号！");
			}
		} catch (Exception e){
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteMallBanner() {
		try {
			if(!Utils.isEmpty(banner) && !Utils.isEmpty(banner.getId())){
				mallBannerService.delete(banner);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e){
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public MallBannerService getMallBannerService() {
		return mallBannerService;
	}
	@Resource
	public void setMallBannerService(MallBannerService mallBannerService) {
		this.mallBannerService = mallBannerService;
	}
	public List<MallBanner> getBannerList() {
		return bannerList;
	}
	public void setBannerList(List<MallBanner> bannerList) {
		this.bannerList = bannerList;
	}
	public MallBanner getBanner() {
		return banner;
	}
	public void setBanner(MallBanner banner) {
		this.banner = banner;
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
