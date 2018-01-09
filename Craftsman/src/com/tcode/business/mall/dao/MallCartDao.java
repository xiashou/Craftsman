package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallCart;

public interface MallCartDao {

	/**
	 * 根据ID查找
	 * @param goodsId
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public MallCart loadById(String goodsId, Integer memId) throws Exception;
	
	/**
	 * 根据会员查找
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<MallCart> loadByMemId(Integer memId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallCart cart) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallCart cart) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallCart cart) throws Exception;
	
}
