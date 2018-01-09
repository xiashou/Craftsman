package com.tcode.business.finance.dao;

import java.util.List;

import com.tcode.business.finance.model.Assets;

public interface AssetsDao {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Assets loadById(Integer id) throws Exception;
	
	/**
	 * 查询店铺所有资产
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Assets> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据ID修改金额
	 * @param id
	 * @param price
	 * @throws Exception
	 */
	public void editPriceById(Integer id, Double price) throws Exception;
	
	/**
	 * 根据代码修改金额
	 * @param code
	 * @param price
	 * @throws Exception
	 */
	public void editPriceByCode(String deptCode, String code, Double price) throws Exception;
	
	/**
	 * 添加
	 * @param assets
	 * @throws Exception
	 */
	public void save(Assets assets) throws Exception;
	
	/**
	 * 修改
	 * @param assets
	 * @throws Exception
	 */
	public void edit(Assets assets) throws Exception;
	
	/**
	 * 删除
	 * @param assets
	 * @throws Exception
	 */
	public void remove(Assets assets) throws Exception;
	
	
	
}
