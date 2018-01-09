package com.tcode.business.finance.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Repayment;
import com.tcode.business.finance.service.RepaymentService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("repaymentAction")
public class RepaymentAction extends BaseAction {

	private static final long serialVersionUID = 4680230813193144844L;
	private static Logger log = Logger.getLogger("SLog");
	
	private RepaymentService repaymentService;
	
	private List<Repayment> repayList;
	
	private Repayment repayment;
	
	
	/**
	 * 根据应收ID查询还款记录
	 * @return
	 */
	public String queryRepaymentByReceId() {
		try {
			if(!Utils.isEmpty(repayment))
				repayList = repaymentService.getByReceId(this.getDept().getDeptCode(), repayment.getMemId(), repayment.getCarId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据供应商ID查询付款记录
	 * @return
	 */
	public String queryRepaymentBySupplierId() {
		try {
			if(!Utils.isEmpty(repayment))
				repayList = repaymentService.getBySupplierId(this.getDept().getDeptCode(), repayment.getSupplierId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	/**
	 * 还一笔款
	 * @return
	 */
	public String insertRepayment() {
		try {
			if(!Utils.isEmpty(repayment)) {
				repayment.setDeptCode(this.getDept().getDeptCode());
				repayment.setCreator(this.getUser().getUserId());
				repayment.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				repaymentService.insert(repayment);
			}
			this.setResult(true, "还款成功！");
		} catch(Exception e) {
			this.setResult(false, "还款失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	

	public RepaymentService getRepaymentService() {
		return repaymentService;
	}
	@Resource
	public void setRepaymentService(RepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public List<Repayment> getRepayList() {
		return repayList;
	}

	public void setRepayList(List<Repayment> repayList) {
		this.repayList = repayList;
	}

	public Repayment getRepayment() {
		return repayment;
	}

	public void setRepayment(Repayment repayment) {
		this.repayment = repayment;
	}
	
	

}
