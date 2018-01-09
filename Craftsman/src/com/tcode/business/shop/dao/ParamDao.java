package com.tcode.business.shop.dao;

import com.tcode.business.shop.model.Param;

public interface ParamDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return Param
	 * @throws Exception
	 */
	public Param loadById(String id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Param param) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Param param) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Param param) throws Exception;
	
}
