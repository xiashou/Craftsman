package com.tcode.business.goods.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsHourRemind;
import com.tcode.business.goods.service.GoodsHourRemindService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsHourRemindAction")
public class GoodsHourRemindAction extends BaseAction {

	private static final long serialVersionUID = -7806180381242759905L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsHourRemindService goodsHourRemindService;
	
	private List<GoodsHourRemind> remindList;
	private GoodsHourRemind remind;
	
	/**
	 * 查询门店所有提醒
	 * @return
	 */
	public String queryAllHourRemind() {
		try {
			remindList = goodsHourRemindService.getHourRemindByDeptCode(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 添加工时类提醒
	 * @return
	 */
	public String insertHourRemind() {
		try {
			if(!Utils.isEmpty(remind)){
				remind.setDeptCode(this.getDept().getDeptCode());
				goodsHourRemindService.insert(remind);
				this.setResult(true, "添加成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改工时类商品
	 * @return
	 */
	public String updateHourRemind() {
		try {
			if(!Utils.isEmpty(remind) && !Utils.isEmpty(remind.getId())){
				remind.setDeptCode(this.getDept().getDeptCode());
				goodsHourRemindService.update(remind);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除工时类商品
	 * @return
	 */
	public String deleteHourRemind() {
		try {
			if(!Utils.isEmpty(remind) && !Utils.isEmpty(remind.getId())){
				goodsHourRemindService.delete(remind);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	

	public GoodsHourRemindService getGoodsHourRemindService() {
		return goodsHourRemindService;
	}
	@Resource
	public void setGoodsHourRemindService(GoodsHourRemindService goodsHourRemindService) {
		this.goodsHourRemindService = goodsHourRemindService;
	}
	public List<GoodsHourRemind> getRemindList() {
		return remindList;
	}
	public void setRemindList(List<GoodsHourRemind> remindList) {
		this.remindList = remindList;
	}
	public GoodsHourRemind getRemind() {
		return remind;
	}
	public void setRemind(GoodsHourRemind remind) {
		this.remind = remind;
	}
}
