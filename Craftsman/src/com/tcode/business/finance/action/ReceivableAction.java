package com.tcode.business.finance.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Receivable;
import com.tcode.business.finance.service.ReceivableService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("receivableAction")
public class ReceivableAction extends BaseAction {

	private static final long serialVersionUID = -5718217101334247547L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ReceivableService receivableService;
	
	private List<Receivable> rList;
	
	private Receivable receivable;
	
	
	/**
	 * 分页查询店铺所有挂账记录
	 * @return
	 */
	public String queryReceivableByDeptPage() {
		try {
			if(Utils.isEmpty(receivable))
				receivable = new Receivable();
			receivable.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(receivableService.getListByDeptCount(receivable));
			rList = receivableService.getListByDeptPage(receivable, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 结清客户
	 * @return
	 */
	public String updateReceivableDone() {
		try {
			if(!Utils.isEmpty(receivable) && !Utils.isEmpty(receivable.getMemId()) && !Utils.isEmpty(receivable.getCarId())){
				Receivable doneRece = receivableService.getById(this.getDept().getDeptCode(), receivable.getMemId(), receivable.getCarId());
				if(!Utils.isEmpty(doneRece)){
					doneRece.setStatus(1);
					doneRece.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:dd").format(new Date()));
					receivableService.update(doneRece);
					log.warn("RECEIVABLE:" + this.getDept().getDeptCode() + "|" + receivable.getMemId() + "|" + receivable.getCarId() + "|1");
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
	

	public ReceivableService getReceivableService() {
		return receivableService;
	}
	@Resource
	public void setReceivableService(ReceivableService receivableService) {
		this.receivableService = receivableService;
	}

	public List<Receivable> getrList() {
		return rList;
	}

	public void setrList(List<Receivable> rList) {
		this.rList = rList;
	}

	public Receivable getReceivable() {
		return receivable;
	}

	public void setReceivable(Receivable receivable) {
		this.receivable = receivable;
	}
	
}
