package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsMoveItem;

public interface GoodsMoveItemDao {
	
	/**
	 * 根据id查找
	 * @param id
	 * @return GoodsMoveItem
	 * @throws Exception
	 */
	public GoodsMoveItem loadById(Integer moveId, Integer itemNo) throws Exception;
	
	/**
	 * 根据主表ID查找
	 * @param moveId
	 * @return List<GoodsMoveItem>
	 * @throws Exception
	 */
	public List<GoodsMoveItem> loadByMoveId(Integer moveId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsMoveItem moveItem) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsMoveItem moveItem) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsMoveItem moveItem) throws Exception;
	
	

}
