package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsCoupons;

public interface GoodsCouponsDao {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsCoupons loadById(String id) throws Exception;
	
	/**
	 * 查询门店所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsCoupons> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsCoupons coupons) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsCoupons coupons) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsCoupons coupons) throws Exception;
}
