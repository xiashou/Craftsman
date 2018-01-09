package com.tcode.business.finance.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Expenditure;
import com.tcode.business.finance.service.ExpenditureService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("expenditureAction")
public class ExpenditureAction extends BaseAction {

	private static final long serialVersionUID = 6148565486856920834L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ExpenditureService expenditureService;
	
	private List<Expenditure> expendList;
	private Expenditure expend;
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryExpenditureByDeptPage() {
		try {
			if(Utils.isEmpty(expend))
				expend = new Expenditure();
			expend.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(expenditureService.getCountByDept(expend));
			expendList =  expenditureService.getListByDeptPage(expend, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertExpenditure() {
		try {
			if(!Utils.isEmpty(expend)) {
				expend.setDeptCode(this.getDept().getDeptCode());
				expend.setCreator(this.getUser().getRealName());
				expend.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:dd").format(new Date()));
				expenditureService.insert(expend);
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
	public String updateExpenditure() {
		try {
			if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getId())) {
				expend.setDeptCode(this.getDept().getDeptCode());
				expenditureService.update(expend);
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
	public String deleteExpenditure() {
		try {
			if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getId()))
				expenditureService.delete(expend);
			this.setResult(true, "删除成功！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public ExpenditureService getExpenditureService() {
		return expenditureService;
	}
	@Resource
	public void setExpenditureService(ExpenditureService expenditureService) {
		this.expenditureService = expenditureService;
	}
	public List<Expenditure> getExpendList() {
		return expendList;
	}
	public void setExpendList(List<Expenditure> expendList) {
		this.expendList = expendList;
	}
	public Expenditure getExpend() {
		return expend;
	}
	public void setExpend(Expenditure expend) {
		this.expend = expend;
	}
	
}
