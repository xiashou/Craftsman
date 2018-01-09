package com.tcode.business.shop.service;

import com.tcode.business.shop.model.Param;

public interface ParamService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return Param
	 * @throws Exception
	 */
	public Param getById(String id) throws Exception;
	
	/**
	 * 添加
	 * @param Param
	 * @throws Exception
	 */
	public void insert(Param param) throws Exception;
	
	/**
	 * 修改
	 * @param Param
	 * @throws Exception
	 */
	public void update(Param param) throws Exception;
	
	/**
	 * 删除
	 * @param Param
	 * @throws Exception
	 */
	public void delete(Param param) throws Exception;
	
}
