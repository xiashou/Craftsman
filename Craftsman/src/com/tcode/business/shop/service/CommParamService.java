package com.tcode.business.shop.service;

import com.tcode.business.shop.model.CommParam;

public interface CommParamService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return CommParam
	 * @throws Exception
	 */
	public CommParam getById(String id) throws Exception;
	
	/**
	 * 添加
	 * @param CommParam
	 * @throws Exception
	 */
	public void insert(CommParam param) throws Exception;
	
	/**
	 * 修改
	 * @param CommParam
	 * @throws Exception
	 */
	public void update(CommParam param) throws Exception;
	
	/**
	 * 删除
	 * @param CommParam
	 * @throws Exception
	 */
	public void delete(CommParam param) throws Exception;
	
}
