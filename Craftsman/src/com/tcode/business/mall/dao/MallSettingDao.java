package com.tcode.business.mall.dao;

import com.tcode.business.mall.model.MallSetting;

public interface MallSettingDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return MallSetting
	 * @throws Exception
	 */
	public MallSetting loadById(String appId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallSetting setting) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallSetting setting) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallSetting setting) throws Exception;
}
