package com.tcode.business.report.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.report.model.ReptRecharge;
import com.tcode.business.report.service.ReptRechargeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("reptRechargeAction")
public class ReptRechargeAction extends BaseAction {

	private static final long serialVersionUID = -8812681556067383192L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ReptRechargeService reptRechargeService;
	
	private List<ReptRecharge> list;
	private ReptRecharge recharge;
	
	
	/**
	 * 分页查询充值记录
	 * @return
	 */
	public String queryReptRechargeListPage() {
		try {
			if(Utils.isEmpty(recharge))
				recharge = new ReptRecharge();
			recharge.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(reptRechargeService.getListCount(recharge));
			list = reptRechargeService.getListPage(recharge, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	
	
	

	public ReptRechargeService getReptRechargeService() {
		return reptRechargeService;
	}
	@Resource
	public void setReptRechargeService(ReptRechargeService reptRechargeService) {
		this.reptRechargeService = reptRechargeService;
	}
	public List<ReptRecharge> getList() {
		return list;
	}
	public void setList(List<ReptRecharge> list) {
		this.list = list;
	}
	public ReptRecharge getRecharge() {
		return recharge;
	}
	public void setRecharge(ReptRecharge recharge) {
		this.recharge = recharge;
	}
	
}
