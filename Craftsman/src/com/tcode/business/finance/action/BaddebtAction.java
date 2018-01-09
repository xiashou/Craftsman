package com.tcode.business.finance.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Baddebt;
import com.tcode.business.finance.service.BaddebtService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("baddebtAction")
public class BaddebtAction extends BaseAction {

	private static final long serialVersionUID = 2515137351473116636L;
	private static Logger log = Logger.getLogger("SLog");
	
	private BaddebtService baddebtService;
	
	private List<Baddebt> debtList;
	private Baddebt debt;
	
	/**
	 * 分页查询坏账
	 * @return
	 */
	public String queryBaddebtListPage() {
		try {
			if(Utils.isEmpty(debt))
				debt = new Baddebt();
			debt.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(baddebtService.getByDeptCount(debt));
			baddebtService.getByDeptPage(debt, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public BaddebtService getBaddebtService() {
		return baddebtService;
	}
	@Resource
	public void setBaddebtService(BaddebtService baddebtService) {
		this.baddebtService = baddebtService;
	}
	public List<Baddebt> getDebtList() {
		return debtList;
	}
	public void setDebtList(List<Baddebt> debtList) {
		this.debtList = debtList;
	}
	public Baddebt getDebt() {
		return debt;
	}
	public void setDebt(Baddebt debt) {
		this.debt = debt;
	}
	

}
