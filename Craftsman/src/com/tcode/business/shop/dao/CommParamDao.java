package com.tcode.business.shop.dao;

import com.tcode.business.shop.model.CommParam;

public interface CommParamDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return Param
	 * @throws Exception
	 */
	public CommParam loadById(String id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(CommParam param) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(CommParam param) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(CommParam param) throws Exception;
	
}
