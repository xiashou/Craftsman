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

import com.tcode.business.mall.model.MallBrand;
import com.tcode.business.mall.service.MallBrandService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Pinyin;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallBrandAction")
public class MallBrandAction extends BaseAction {
	
	private static final long serialVersionUID = 335619606425463046L;
	private static Logger log = Logger.getLogger("SLog");

	private MallBrandService mallBrandService;
	
	private List<MallBrand> brandList;
	private MallBrand mallBrand;
	
	private File upload;
	private String uploadFileName;
	
	
	/**
	 * 分页查找商城品牌信息
	 * @return
	 */
	public String queryMallBrandByPage() {
		try {
			if(Utils.isEmpty(mallBrand))
				mallBrand = new MallBrand();
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				mallBrand.setAppId(wechatApp.getAuthorizerAppId());
				this.setTotalCount(mallBrandService.getListCount(mallBrand));
				brandList = mallBrandService.getListPage(mallBrand, this.getStart(), this.getLimit());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertMallBrand() {
		try {
			if(!Utils.isEmpty(mallBrand)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					mallBrand.setAppId(wechatApp.getAuthorizerAppId());
					if (!Utils.isEmpty(upload)) {
			        	String realpath = ServletActionContext.getServletContext().getRealPath("/upload/mall/brand");
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
			        	mallBrand.setLogo(uploadFileName);
			        }
					mallBrand.setShortCode(Pinyin.getPinYinHeadChar(mallBrand.getBrandName()));
					mallBrandService.insert(mallBrand);
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
	
	/**
	 * 修改
	 * @return
	 */
	public String updateMallBrand() {
		try {
			if(!Utils.isEmpty(mallBrand) && !Utils.isEmpty(mallBrand.getId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					mallBrand.setAppId(wechatApp.getAuthorizerAppId());
					if (!Utils.isEmpty(upload)) {
			        	String realpath = ServletActionContext.getServletContext().getRealPath("/upload/mall/brand");
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
			        	mallBrand.setLogo(uploadFileName);
			        }
					mallBrand.setShortCode(Pinyin.getPinYinHeadChar(mallBrand.getBrandName()));
					mallBrandService.update(mallBrand);
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
	public String deleteMallBrand() {
		try {
			if(!Utils.isEmpty(mallBrand) && !Utils.isEmpty(mallBrand.getId())){
				mallBrandService.delete(mallBrand);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e){
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public MallBrandService getMallBrandService() {
		return mallBrandService;
	}
	@Resource
	public void setMallBrandService(MallBrandService mallBrandService) {
		this.mallBrandService = mallBrandService;
	}
	public List<MallBrand> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<MallBrand> brandList) {
		this.brandList = brandList;
	}
	public MallBrand getMallBrand() {
		return mallBrand;
	}
	public void setMallBrand(MallBrand mallBrand) {
		this.mallBrand = mallBrand;
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
