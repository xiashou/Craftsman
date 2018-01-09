package com.tcode.business.shop.service;

import com.tcode.business.shop.model.Setting;

public interface SettingService {

	/**
	 * 根据ID查找
	 * @param deptCode
	 * @return Setting
	 * @throws Exception
	 */
	public Setting getById(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @param Setting
	 * @throws Exception
	 */
	public void insert(Setting setting) throws Exception;
	
	/**
	 * 修改
	 * @param Setting
	 * @throws Exception
	 */
	public void update(Setting setting) throws Exception;
	
	/**
	 * 删除
	 * @param Setting
	 * @throws Exception
	 */
	public void delete(Setting setting) throws Exception;
	
}
