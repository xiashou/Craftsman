package com.tcode.business.shop.service;

import java.util.List;

import com.tcode.business.order.model.OrderHead;
import com.tcode.business.shop.model.Recharge;
import com.tcode.business.shop.model.RechargeDetail;

public interface RechargeService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return Recharge
	 * @throws Exception
	 */
	public Recharge getById(Integer id) throws Exception;
	
	/**
	 * 根据门店查询所有
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Recharge> getRechargeListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据ID查询明细
	 * @param rechargeId
	 * @return
	 * @throws Exception
	 */
	public List<RechargeDetail> getDetailListByRecharge(Integer rechargeId) throws Exception;
	
	/**
	 * 根据充值套餐更新会员库存
	 * @param rechargeId
	 * @param detailItem
	 * @return
	 * @throws Exception
	 */
	public void updateMemberStockByRecharge(OrderHead orderHead, Integer rechargeId, String detailItem) throws Exception;
	
	/**
	 * 添加
	 * @param Recharge
	 * @throws Exception
	 */
	public void insert(Recharge recharge) throws Exception;
	
	/**
	 * 修改
	 * @param Recharge
	 * @throws Exception
	 */
	public void update(Recharge recharge) throws Exception;
	
	/**
	 * 删除
	 * @param Recharge
	 * @throws Exception
	 */
	public void delete(Recharge recharge) throws Exception;
	
	/**
	 * 删除明细
	 * @param Recharge
	 * @throws Exception
	 */
	public void deleteRechargeDetail(Integer rechargeId) throws Exception;
	
	/**
	 * 添加明细
	 * @param recharge
	 * @throws Exception
	 */
	public void insertDetail(RechargeDetail rechargeDetail) throws Exception;

	/**
	 * 修改明细
	 * @param recharge
	 * @throws Exception
	 */
	public void updateDetail(RechargeDetail rechargeDetail) throws Exception;

	/**
	 * 删除明细
	 * @param recharge
	 * @throws Exception
	 */
	public void deleteDetail(RechargeDetail rechargeDetail) throws Exception;
}
