package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallOrderHead;

public interface MallOrderHeadDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return MallOrderHead
	 * @throws Exception
	 */
	public MallOrderHead loadById(String orderId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallOrderHead orderHead) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallOrderHead orderHead) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallOrderHead orderHead) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param orderHead, start,limit
	 * @return List<MallOrderHead>
	 * @throws Exception
	 */
	public List<MallOrderHead> loadListPage(MallOrderHead orderHead, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param orderHead
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(MallOrderHead orderHead) throws Exception;
}
