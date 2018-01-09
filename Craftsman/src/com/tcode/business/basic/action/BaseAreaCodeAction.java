package com.tcode.business.basic.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseAreaCode;
import com.tcode.business.basic.service.BaseAreaCodeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("areaCodeAction")
public class BaseAreaCodeAction extends BaseAction {

	private static final long serialVersionUID = -7571566085084806896L;
	private static Logger log = Logger.getLogger("SLog");
	
	private BaseAreaCodeService areaCodeService;
	
	private List<BaseAreaCode> areaCodeList;
	private BaseAreaCode areaCode;
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryAreaCodePage() {
		try {
			this.setTotalCount(areaCodeService.getListCount(areaCode));
			areaCodeList = areaCodeService.getListPage(areaCode, this.getStart(), this.getLimit());
		} catch(Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public String queryAllAreaCode() {
		try {
			areaCodeList = areaCodeService.getAll();
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertAreaCode() {
		try {
			if(!Utils.isEmpty(areaCode)){
				areaCodeService.insert(areaCode);
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
	public String updateAreaCode() {
		try {
			if(!Utils.isEmpty(areaCode) && !Utils.isEmpty(areaCode.getId())){
				areaCodeService.update(areaCode);
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
	public String deleteAreaCode() {
		try {
			if(!Utils.isEmpty(areaCode) && !Utils.isEmpty(areaCode.getId())){
				areaCodeService.delete(areaCode);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	
	public BaseAreaCodeService getAreaCodeService() {
		return areaCodeService;
	}
	@Resource
	public void setAreaCodeService(BaseAreaCodeService areaCodeService) {
		this.areaCodeService = areaCodeService;
	}
	public List<BaseAreaCode> getAreaCodeList() {
		return areaCodeList;
	}
	public void setAreaCodeList(List<BaseAreaCode> areaCodeList) {
		this.areaCodeList = areaCodeList;
	}
	public BaseAreaCode getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(BaseAreaCode areaCode) {
		this.areaCode = areaCode;
	}
	
}
