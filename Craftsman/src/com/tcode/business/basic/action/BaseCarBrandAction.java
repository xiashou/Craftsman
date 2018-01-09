package com.tcode.business.basic.action;

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

import com.tcode.business.basic.model.BaseCarBrand;
import com.tcode.business.basic.service.BaseCarBrandService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("carBrandAction")
public class BaseCarBrandAction extends BaseAction {

	private static final long serialVersionUID = 3010932635653031134L;
	private static Logger log = Logger.getLogger("SLog");
	
	private BaseCarBrandService carBrandService;
	
	private List<BaseCarBrand> carBrandList;
	private BaseCarBrand carBrand;
	
	private String shortCode;
	private String brandName;
	private File upload;
	private String uploadFileName;
	
	
	/**
	 * 分页查询品牌
	 * @return
	 */
	public String queryCarBrandPage() {
		try {
			if(!Utils.isEmpty(carBrandService)){
				this.setTotalCount(carBrandService.getListCount(carBrand));
				carBrandList = carBrandService.getListPage(carBrand, Utils.isEmpty(this.getStart()) ? 0 : this.getStart(), Utils.isEmpty(this.getLimit()) ? 20 : this.getLimit());
			}
		} catch(Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有品牌
	 * @return
	 */
	public String queryAllCarBrand() {
		try {
//			this.setTotalCount(carBrandService.getListCount(carBrand));
			carBrandList = carBrandService.getAll();
		} catch(Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertCarBrand() {
		try {
			if(!Utils.isEmpty(carBrand)){
				if(!Utils.isEmpty(upload)){
        			String realpath = ServletActionContext.getServletContext().getRealPath("/upload/brand");
        			String suffix;
        			if(uploadFileName != null && !"".equals(uploadFileName) && uploadFileName.indexOf(".") > 0)
        				suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
        			else
        				suffix = "";
        			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
        			File savefile = new File(new File(realpath), fileName);
        			if (!savefile.getParentFile().exists())
        				savefile.getParentFile().mkdirs();
        			FileUtils.copyFile(upload, savefile);
        			carBrand.setLogo(fileName);
        		}
				carBrandService.insert(carBrand);
				this.setResult(true, "添加成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateCarBrand() {
		try {
			if(!Utils.isEmpty(carBrand) && !Utils.isEmpty(carBrand.getId())){
        		if(!Utils.isEmpty(upload)){
        			String realpath = ServletActionContext.getServletContext().getRealPath("/upload/brand");
        			String suffix;
        			if(uploadFileName != null && !"".equals(uploadFileName) && uploadFileName.indexOf(".") > 0)
        				suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
        			else
        				suffix = "";
        			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
        			File savefile = new File(new File(realpath), fileName);
        			if (!savefile.getParentFile().exists())
        				savefile.getParentFile().mkdirs();
        			FileUtils.copyFile(upload, savefile);
        			carBrand.setLogo(fileName);
        		}
				carBrandService.update(carBrand);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteCarBrand() {
		try {
			if(!Utils.isEmpty(carBrand) && !Utils.isEmpty(carBrand.getId())){
				carBrandService.delete(carBrand);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public BaseCarBrandService getCarBrandService() {
		return carBrandService;
	}
	@Resource
	public void setCarBrandService(BaseCarBrandService carBrandService) {
		this.carBrandService = carBrandService;
	}
	public List<BaseCarBrand> getCarBrandList() {
		return carBrandList;
	}
	public void setCarBrandList(List<BaseCarBrand> carBrandList) {
		this.carBrandList = carBrandList;
	}
	public BaseCarBrand getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(BaseCarBrand carBrand) {
		this.carBrand = carBrand;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
