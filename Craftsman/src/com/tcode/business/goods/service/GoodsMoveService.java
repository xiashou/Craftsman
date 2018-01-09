package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsMoveHead;
import com.tcode.business.goods.model.GoodsMoveItem;

public interface GoodsMoveService {

	/**
	 * 根据调入门店查询调入列表
	 * @param moveIn
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMoveHead> getByMoveIn(String moveIn) throws Exception;
	
	/**
	 * 根据调出门店查询调出列表
	 * @param moveOut
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMoveHead> getByMoveOut(String moveOut) throws Exception;
	
	/**
	 * 根据门店查询所有调拨记录
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMoveHead> getByDept(String dept) throws Exception;
	
	/**
	 * 根据id查询调拨商品列表
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMoveItem> getItemListByMoveId(Integer id) throws Exception;
	
	/**
	 * 分页查询调拨记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMoveHead> getListByPage(GoodsMoveHead moveHead, int start, int limit) throws Exception;
	
	/**
	 * 调拨记录数
	 * @param moveHead
	 * @return
	 * @throws Exception
	 */
	public Integer getListByCount(GoodsMoveHead moveHead) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insertGoodsMove(GoodsMoveHead moveHead, List<GoodsMoveItem> moveItemList) throws Exception;
	
	/**
	 * 收货
	 * @throws Exception
	 */
	public void updateReceiptGoodsMove(Integer moveId, String userName) throws Exception;
	
}
