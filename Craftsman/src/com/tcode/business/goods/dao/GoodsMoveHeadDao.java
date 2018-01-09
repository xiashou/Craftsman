package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsMoveHead;

public interface GoodsMoveHeadDao {
	
	/**
	 * 根据id查找
	 * @param id
	 * @return GoodsMoveHead
	 * @throws Exception
	 */
	public GoodsMoveHead loadById(Integer id) throws Exception;
	
	/**
	 * 根据调入门店从查找
	 * @param moveIn
	 * @return List<GoodsMoveHead>
	 * @throws Exception
	 */
	public List<GoodsMoveHead> loadByMoveIn(String moveIn) throws Exception;
	
	/**
	 * 根据调入门店从查找
	 * @param moveOut
	 * @return List<moveOut>
	 * @throws Exception
	 */
	public List<GoodsMoveHead> loadByMoveOut(String moveOut) throws Exception;
	
	/**
	 * 分页查询门店调拨记录
	 * @param moveItem
	 * @return List<GoodsMoveItem>
	 * @throws Exception
	 */
	public List<GoodsMoveHead> loadListByPage(GoodsMoveHead moveHead, int start, int limit) throws Exception;
	
	/**
	 * 分页查询门店调拨记录数量
	 * @param moveItem
	 * @return List<GoodsMoveItem>
	 * @throws Exception
	 */
	public Integer loadListByCount(GoodsMoveHead moveHead) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsMoveHead moveHead) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsMoveHead moveHead) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsMoveHead moveHead) throws Exception;
	
	

}
