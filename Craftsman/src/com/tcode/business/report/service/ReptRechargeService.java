package com.tcode.business.report.service;

import java.util.List;

import com.tcode.business.report.model.ReptRecharge;

public interface ReptRechargeService {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptRecharge
	 * @throws Exception
	 */
	public ReptRecharge getReptRechargeById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据会员ID查找
	 * @param memId
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> getListByMemId(Integer memId) throws Exception;
	
	/**
	 * 根据订单ID和商品ID查询
	 * @param orderId
	 * @param goodsId
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> getByOrder(String orderId, String goodsId) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param recharge, start, limit
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> getListPage(ReptRecharge recharge, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ReptRecharge
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getListCount(ReptRecharge recharge) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(ReptRecharge recharge) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(ReptRecharge recharge) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(ReptRecharge recharge) throws Exception;

}
