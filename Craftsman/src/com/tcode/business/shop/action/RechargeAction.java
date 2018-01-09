package com.tcode.business.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.shop.model.Recharge;
import com.tcode.business.shop.model.RechargeDetail;
import com.tcode.business.shop.service.RechargeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("rechargeAction")
public class RechargeAction extends BaseAction {

	private static final long serialVersionUID = 6552734402241528303L;
	private static Logger log = Logger.getLogger("SLog");
	
	private RechargeService rechargeService;
	
	private List<Recharge> rList;
	private List<RechargeDetail> dList;
	
	private Recharge recharge;
	private RechargeDetail rechargeDetail;
	
	/**
	 * 查询门店所有充值设置（表头信息）
	 * @return
	 */
	public String queryRechargeByDept() {
		try {
			rList = rechargeService.getRechargeListByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据表头赠送ID查询明细
	 * @return
	 */
	public String queryRechargeDetailById() {
		try {
			if(!Utils.isEmpty(recharge) && !Utils.isEmpty(recharge.getId())){
				dList = rechargeService.getDetailListByRecharge(recharge.getId());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertRecharge() {
		try {
			if(!Utils.isEmpty(recharge)){
				recharge.setDeptCode(this.getDept().getDeptCode());
				rechargeService.insert(recharge);
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
	public String updateRecharge() {
		try {
			if(!Utils.isEmpty(recharge) && !Utils.isEmpty(recharge.getId())){
				recharge.setDeptCode(this.getDept().getDeptCode());
				rechargeService.update(recharge);
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
	public String deleteRecharge() {
		try {
			if(!Utils.isEmpty(recharge) && !Utils.isEmpty(recharge.getId())){
				rechargeService.delete(recharge);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加赠送项目
	 * @return
	 */
	public String insertRechargeDetail() {
		try {
			if(!Utils.isEmpty(rechargeDetail)){
				rechargeService.insertDetail(rechargeDetail);
				this.setResult(true, "添加成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改赠送项目
	 * @return
	 */
	public String updateRechargeDetail() {
		try {
			if(!Utils.isEmpty(rechargeDetail) && !Utils.isEmpty(rechargeDetail.getRechargeId())){
				rechargeService.updateDetail(rechargeDetail);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除赠送项目
	 * @return
	 */
	public String deleteRechargeDetail() {
		try {
			if(!Utils.isEmpty(rechargeDetail) && !Utils.isEmpty(rechargeDetail.getRechargeId())){
				rechargeService.deleteDetail(rechargeDetail);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	
	public RechargeService getRechargeService() {
		return rechargeService;
	}
	@Resource
	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}
	public List<Recharge> getrList() {
		return rList;
	}
	public void setrList(List<Recharge> rList) {
		this.rList = rList;
	}
	public List<RechargeDetail> getdList() {
		return dList;
	}
	public void setdList(List<RechargeDetail> dList) {
		this.dList = dList;
	}
	public Recharge getRecharge() {
		return recharge;
	}
	public void setRecharge(Recharge recharge) {
		this.recharge = recharge;
	}
	public RechargeDetail getRechargeDetail() {
		return rechargeDetail;
	}
	public void setRechargeDetail(RechargeDetail rechargeDetail) {
		this.rechargeDetail = rechargeDetail;
	}
	
}
