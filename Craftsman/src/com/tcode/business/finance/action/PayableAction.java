package com.tcode.business.finance.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Payable;
import com.tcode.business.finance.service.PayableService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("payableAction")
public class PayableAction extends BaseAction {

	private static final long serialVersionUID = -8241674892477504373L;
	private static Logger log = Logger.getLogger("SLog");
	
	private PayableService payableService;
	
	private List<Payable> payableList;
	private Payable payable;
	
	
	/**
	 * 分页查询店铺所有应付记录
	 * @return
	 */
	public String queryPayableByDeptPage() {
		try {
			if(Utils.isEmpty(payable))
				payable = new Payable();
			payable.setDeptCode(this.getDept().getDeptCode());
			payableList = payableService.getListByDeptPage(payable, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 结清供应商
	 * @return
	 */
	public String updatePayableDone() {
		try {
			if(!Utils.isEmpty(payable) && !Utils.isEmpty(payable.getSupplierId())){
				Payable donePay = payableService.getById(this.getDept().getDeptCode(), payable.getSupplierId());
				if(!Utils.isEmpty(donePay)){
					donePay.setStatus(1);
					donePay.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:dd").format(new Date()));
					payableService.update(donePay);
					log.warn("PAYABLE:" + this.getDept().getDeptCode() + "|" + payable.getSupplierId() + "|" + "|1");
					this.setResult(true, "操作成功！");
				} else
					this.setResult(false, "没有找到相关记录！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e){
			this.setResult(false, "发生错误！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	
	public PayableService getPayableService() {
		return payableService;
	}
	@Resource
	public void setPayableService(PayableService payableService) {
		this.payableService = payableService;
	}
	public List<Payable> getPayableList() {
		return payableList;
	}
	public void setPayableList(List<Payable> payableList) {
		this.payableList = payableList;
	}
	public Payable getPayable() {
		return payable;
	}
	public void setPayable(Payable payable) {
		this.payable = payable;
	}
	
}
