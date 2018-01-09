package com.tcode.business.basic.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseCarSeries;
import com.tcode.business.basic.service.BaseCarSeriesService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("carSeriesAction")
public class BaseCarSeriesAction extends BaseAction {

	private static final long serialVersionUID = 2896557688347068143L;
	private static Logger log = Logger.getLogger("SLog");

	private BaseCarSeriesService carSeriesService;
	
	private List<BaseCarSeries> carSeriesList;
	private BaseCarSeries carSeries;
	private Integer brandId;
	
	/**
	 * 查询所有
	 * @return
	 */
	public String queryAllCarSeries() {
		try {
			carSeriesList = carSeriesService.getAll();
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据品牌ID查找
	 * @return
	 */
	public String queryCarSeriesByBrandId() {
		try {
			if(!Utils.isEmpty(brandId))
				carSeriesList = carSeriesService.getByBrandId(brandId);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertCarSeries() {
		try {
			if(!Utils.isEmpty(carSeries)){
				carSeriesService.insert(carSeries);
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
	public String updateCarSeries() {
		try {
			if(!Utils.isEmpty(carSeries) && !Utils.isEmpty(carSeries.getId())){
				carSeriesService.update(carSeries);
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
	public String deleteCarSeries() {
		try {
			if(!Utils.isEmpty(carSeries) && !Utils.isEmpty(carSeries.getId())){
				carSeriesService.delete(carSeries);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public BaseCarSeriesService getCarSeriesService() {
		return carSeriesService;
	}
	@Resource
	public void setCarSeriesService(BaseCarSeriesService carSeriesService) {
		this.carSeriesService = carSeriesService;
	}

	public List<BaseCarSeries> getCarSeriesList() {
		return carSeriesList;
	}

	public void setCarSeriesList(List<BaseCarSeries> carSeriesList) {
		this.carSeriesList = carSeriesList;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public BaseCarSeries getCarSeries() {
		return carSeries;
	}

	public void setCarSeries(BaseCarSeries carSeries) {
		this.carSeries = carSeries;
	}
	
}
