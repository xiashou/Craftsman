package com.tcode.business.basic.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseCarModel;
import com.tcode.business.basic.service.BaseCarModelService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("carModelAction")
public class BaseCarModelAction extends BaseAction {

	private static final long serialVersionUID = 5284264131559201106L;
	private static Logger log = Logger.getLogger("SLog");
	
	private BaseCarModelService carModelService;
	
	private List<BaseCarModel> carModelList;
	private BaseCarModel carModel;
	
	/**
	 * 查询所有
	 * @return
	 */
	public String queryAllCarModel() {
		try {
			carModelList = carModelService.getAll();
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据系列ID查找
	 * @return
	 */
	public String queryCarModelBySeriesId() {
		try {
			if(!Utils.isEmpty(carModel) && !Utils.isEmpty(carModel.getSeriesId()))
				carModelList = carModelService.getBySeriesId(carModel.getSeriesId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertCarModel() {
		try {
			if(!Utils.isEmpty(carModel)){
				carModelService.insert(carModel);
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
	public String updateCarModel() {
		try {
			if(!Utils.isEmpty(carModel) && !Utils.isEmpty(carModel.getId())){
				carModelService.update(carModel);
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
	public String deleteCarModel() {
		try {
			if(!Utils.isEmpty(carModel) && !Utils.isEmpty(carModel.getId())){
				carModelService.delete(carModel);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public BaseCarModelService getCarModelService() {
		return carModelService;
	}
	@Resource
	public void setCarModelService(BaseCarModelService carModelService) {
		this.carModelService = carModelService;
	}
	public List<BaseCarModel> getCarModelList() {
		return carModelList;
	}
	public void setCarModelList(List<BaseCarModel> carModelList) {
		this.carModelList = carModelList;
	}
	public BaseCarModel getCarModel() {
		return carModel;
	}
	public void setCarModel(BaseCarModel carModel) {
		this.carModel = carModel;
	}

}
