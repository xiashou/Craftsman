package com.tcode.business.shop.dao;

import com.tcode.business.shop.model.Setting;

public interface SettingDao {

	/**
	 * 根据id查找
	 * @param deptCode
	 * @return Param
	 * @throws Exception
	 */
	public Setting loadById(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Setting setting) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Setting setting) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Setting setting) throws Exception;
	
}
