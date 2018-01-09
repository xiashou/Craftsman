package com.tcode.business.finance.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Exptype;
import com.tcode.business.finance.service.ExptypeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("exptypeAction")
public class ExptypeAction extends BaseAction {

	private static final long serialVersionUID = -6553022549180606490L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ExptypeService exptypeService;
	
	private List<Exptype> typeList;
	private Exptype exptype;
	
	/**
	 * 查询门店所有支出类型
	 * @return
	 */
	public String queryExptypeListByDept() {
		try {
			typeList = exptypeService.getListByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertExptype() {
		try {
			if(!Utils.isEmpty(exptype)) {
				exptype.setDeptCode(this.getDept().getDeptCode());
				exptypeService.insert(exptype);
			}
			this.setResult(true, "添加成功！");
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
	public String updateExptype() {
		try {
			if(!Utils.isEmpty(exptype) && !Utils.isEmpty(exptype.getId())) {
				exptype.setDeptCode(this.getDept().getDeptCode());
				exptypeService.update(exptype);
			}
			this.setResult(true, "修改成功！");
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
	public String deleteExptype() {
		try {
			if(!Utils.isEmpty(exptype) && !Utils.isEmpty(exptype.getId()))
				exptypeService.delete(exptype);
			this.setResult(true, "删除成功！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public ExptypeService getExptypeService() {
		return exptypeService;
	}
	@Resource
	public void setExptypeService(ExptypeService exptypeService) {
		this.exptypeService = exptypeService;
	}
	public List<Exptype> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<Exptype> typeList) {
		this.typeList = typeList;
	}
	public Exptype getExptype() {
		return exptype;
	}
	public void setExptype(Exptype exptype) {
		this.exptype = exptype;
	}
	
}
