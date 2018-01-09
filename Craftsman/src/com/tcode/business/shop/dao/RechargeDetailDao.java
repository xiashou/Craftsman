package com.tcode.business.shop.dao;

import java.util.List;

import com.tcode.business.shop.model.RechargeDetail;

public interface RechargeDetailDao {

	/**
	 * 根据部门查询所有
	 * @param id
	 * @return RechargeDetail
	 * @throws Exception
	 */
	public List<RechargeDetail> loadByRechargeId(Integer rechargeId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(RechargeDetail rDetail) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(RechargeDetail rDetail) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(RechargeDetail rDetail) throws Exception;
	
	/**
	 * 根据ID删除
	 * @throws Exception
	 */
	public void removeByRechargeId(Integer rechargeId) throws Exception;
}
