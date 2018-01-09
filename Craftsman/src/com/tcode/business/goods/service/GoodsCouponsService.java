package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsCoupons;

public interface GoodsCouponsService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsCoupons getCouponsById(String id) throws Exception;
	
	/**
	 * 查询门店所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsCoupons> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(GoodsCoupons coupons) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(GoodsCoupons coupons) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(GoodsCoupons coupons) throws Exception;
}
