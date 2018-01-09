package com.tcode.business.basic.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseArea;
import com.tcode.business.basic.service.BaseAreaService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("baseAreaAction")
public class BaseAreaAction extends BaseAction {

	private static final long serialVersionUID = 335619606425463046L;
	private static Logger log = Logger.getLogger("SLog");
	
	private BaseAreaService baseAreaService;
	
	private List<BaseArea> areaList;
	private BaseArea area;
	private String areaId;
	
	/**
	 * 分页查询区域信息
	 * @return
	 */
	public String queryBaseAreaPage() {
		try { 
			this.setTotalCount(baseAreaService.getListCount(area));
			areaList = baseAreaService.getListPage(area, this.getStart(), this.getLimit());
		} catch (Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有区域信息
	 * @return
	 */
	public String queryAllBaseArea() {
		try {
			areaList = baseAreaService.getAll();
		} catch (Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有省份区域信息
	 * @return
	 */
	public String queryProvinceBaseArea() {
		try {
			areaList = baseAreaService.getAllProvince();
		} catch (Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询区域树
	 * @return
	 */
	public String queryBaseAreaTree() {
		try {
			if(Utils.isEmpty(areaId))
				areaList = baseAreaService.getBaseAreaTree();
			else
				areaList = baseAreaService.getBaseAreaTreeByAgent(areaId);
		} catch (Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询
	 * @return
	 */
	public String queryBaseAreaById() {
		try {
			if(!Utils.isEmpty(area) && !Utils.isEmpty(area.getAreaId())){
				area = baseAreaService.getAreaById(area.getAreaId());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加区域信息
	 * @return
	 */
	public String insertBaseArea() {
		try {
			if(!Utils.isEmpty(area)){
				baseAreaService.insert(area);
				this.setResult(true, "添加成功！");
			}
		} catch (Exception e){
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改区域信息
	 * @return
	 */
	public String updateBaseArea() {
		try {
			if(!Utils.isEmpty(area) && !Utils.isEmpty(area.getAreaId())){
				baseAreaService.update(area);
				this.setResult(true, "修改成功！");
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
	public String deleteBaseArea() {
		try {
			if(!Utils.isEmpty(area) && !Utils.isEmpty(area.getAreaId())){
				baseAreaService.delete(area);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e){
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public BaseAreaService getBaseAreaService() {
		return baseAreaService;
	}
	@Resource
	public void setBaseAreaService(BaseAreaService baseAreaService) {
		this.baseAreaService = baseAreaService;
	}
	public List<BaseArea> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<BaseArea> areaList) {
		this.areaList = areaList;
	}
	public BaseArea getArea() {
		return area;
	}
	public void setArea(BaseArea area) {
		this.area = area;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
