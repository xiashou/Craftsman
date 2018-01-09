package com.tcode.business.goods.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsCoupons;
import com.tcode.business.goods.service.GoodsCouponsService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.idgenerator.IDHelper;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsCouponsAction")
public class GoodsCouponsAction extends BaseAction {

	private static final long serialVersionUID = -7948598044013066282L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsCouponsService goodsCouponsService;
	
	private List<GoodsCoupons> couponsList;
	private GoodsCoupons coupons;
	
	/**
	 * 查询门店所有卡券
	 * @return
	 */
	public String queryCouponsByDept() {
		try {
			couponsList = goodsCouponsService.getListByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询
	 * @return
	 */
	public String queryCouponsById() {
		try {
			if(!Utils.isEmpty(coupons)){
				coupons = goodsCouponsService.getCouponsById(coupons.getId());
				couponsList = new ArrayList<GoodsCoupons>();
				couponsList.add(coupons);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			this.setResult(false, "获取卡券信息出错！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加工时类提醒
	 * @return
	 */
	public String insertCoupons() {
		try {
			if(!Utils.isEmpty(coupons)){
				coupons.setId(IDHelper.getCouponsId());
				coupons.setDeptCode(this.getDept().getDeptCode());
				goodsCouponsService.insert(coupons);
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
	public String updateCoupons() {
		try {
			if(!Utils.isEmpty(coupons) && !Utils.isEmpty(coupons.getId())){
				coupons.setDeptCode(this.getDept().getDeptCode());
				goodsCouponsService.update(coupons);
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
	public String deleteCoupons() {
		try {
			if(!Utils.isEmpty(coupons) && !Utils.isEmpty(coupons.getId())){
				goodsCouponsService.delete(coupons);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public GoodsCouponsService getGoodsCouponsService() {
		return goodsCouponsService;
	}
	@Resource
	public void setGoodsCouponsService(GoodsCouponsService goodsCouponsService) {
		this.goodsCouponsService = goodsCouponsService;
	}
	public List<GoodsCoupons> getCouponsList() {
		return couponsList;
	}
	public void setCouponsList(List<GoodsCoupons> couponsList) {
		this.couponsList = couponsList;
	}
	public GoodsCoupons getCoupons() {
		return coupons;
	}
	public void setCoupons(GoodsCoupons coupons) {
		this.coupons = coupons;
	}

}
