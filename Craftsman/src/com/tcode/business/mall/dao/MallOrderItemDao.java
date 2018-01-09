package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallOrderItem;

public interface MallOrderItemDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return MallOrderItem
	 * @throws Exception
	 */
	public List<MallOrderItem> loadListByOrderId(String orderId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallOrderItem orderItem) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallOrderItem orderItem) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallOrderItem orderItem) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void removeByOrderId(String orderId) throws Exception;
}
