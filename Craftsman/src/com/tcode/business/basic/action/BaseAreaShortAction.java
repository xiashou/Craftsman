package com.tcode.business.basic.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseAreaShort;
import com.tcode.business.basic.service.BaseAreaShortService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("areaShortAction")
public class BaseAreaShortAction extends BaseAction {

	private static final long serialVersionUID = 4668036367015254905L;
	private static Logger log = Logger.getLogger("SLog");
	
	private BaseAreaShortService areaShortService;
	
	private List<BaseAreaShort> areaShortList;
	private BaseAreaShort areaShort;
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryAreaShortPage() {
		try {
			this.setTotalCount(areaShortService.getListCount(areaShort));
			areaShortList = areaShortService.getListPage(areaShort, this.getStart(), this.getLimit());
		} catch(Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public String queryAllAreaShort() {
		try {
			areaShortList = areaShortService.getAll();
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertAreaShort() {
		try {
			if(!Utils.isEmpty(areaShort)){
				areaShortService.insert(areaShort);
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
	public String updateAreaShort() {
		try {
			if(!Utils.isEmpty(areaShort) && !Utils.isEmpty(areaShort.getId())){
				areaShortService.update(areaShort);
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
	public String deleteAreaShort() {
		try {
			if(!Utils.isEmpty(areaShort) && !Utils.isEmpty(areaShort.getId())){
				areaShortService.delete(areaShort);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public BaseAreaShortService getAreaShortService() {
		return areaShortService;
	}
	@Resource
	public void setAreaShortService(BaseAreaShortService areaShortService) {
		this.areaShortService = areaShortService;
	}
	public List<BaseAreaShort> getAreaShortList() {
		return areaShortList;
	}
	public void setAreaShortList(List<BaseAreaShort> areaShortList) {
		this.areaShortList = areaShortList;
	}
	public BaseAreaShort getAreaShort() {
		return areaShort;
	}
	public void setAreaShort(BaseAreaShort areaShort) {
		this.areaShort = areaShort;
	}

}
