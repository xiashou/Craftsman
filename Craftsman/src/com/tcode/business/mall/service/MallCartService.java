package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallCart;

public interface MallCartService {

	/**
	 * 根据ID查询
	 * @param goodsId
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public MallCart getById(String goodsId, Integer memId) throws Exception;
	
	/**
	 * 查询会员购物车
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<MallCart> getListByMemId(Integer memId) throws Exception;
	
	public void insert(MallCart cart) throws Exception;
	
	public void update(MallCart cart) throws Exception;
	
	public void delete(MallCart cart) throws Exception;
}
