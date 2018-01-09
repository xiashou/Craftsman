package com.tcode.business.report.dao;

import java.util.List;

import com.tcode.business.report.model.ReptRecharge;

public interface ReptRechargeDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptRecharge
	 * @throws Exception
	 */
	public ReptRecharge loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据会员ID查找
	 * @param memId
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> loadByMemId(Integer memId) throws Exception;
	
	/**
	 * 根据订单ID和商品ID查询
	 * @param orderId
	 * @param goodsId
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> loadByOrder(String orderId, String goodsId) throws Exception;
	
	/**
	 * 添加一条充值记录
	 * @throws Exception
	 */
	public void addRecord(String deptCode, Integer memId, String orderNo, String goodsId, String goodsName, Double number) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ReptRecharge recharge) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ReptRecharge recharge) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ReptRecharge recharge) throws Exception;
	
	/**
	 * 根据订单删除记录
	 * @param id
	 * @return Recharge
	 * @throws Exception
	 */
	public void removeByOrder(String orderNo) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param recharge, start, limit
	 * @return List<ReptRecharge>
	 * @throws Exception
	 */
	public List<ReptRecharge> loadListPage(ReptRecharge recharge, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ReptRecharge
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(ReptRecharge recharge) throws Exception;
	
	
}
