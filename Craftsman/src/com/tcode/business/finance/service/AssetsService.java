package com.tcode.business.finance.service;

import java.util.List;

import com.tcode.business.finance.model.Assets;

public interface AssetsService {

	/**
	 * 根据ID查询资产
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Assets getById(Integer id) throws Exception;
	
	/**
	 * 查询店铺所有资产
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Assets> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @param assets
	 * @throws Exception
	 */
	public void insert(Assets assets) throws Exception;
	
	/**
	 * 修改
	 * @param assets
	 * @throws Exception
	 */
	public void update(Assets assets) throws Exception;
	
	/**
	 * 删除
	 * @param assets
	 * @throws Exception
	 */
	public void delete(Assets assets) throws Exception;
}
