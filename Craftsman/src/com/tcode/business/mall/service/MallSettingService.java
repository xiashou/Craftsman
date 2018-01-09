package com.tcode.business.mall.service;

import com.tcode.business.mall.model.MallSetting;

public interface MallSettingService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return MallSetting
	 * @throws Exception
	 */
	public MallSetting getById(String appId) throws Exception;
	
	/**
	 * 添加
	 * @param MallSetting
	 * @throws Exception
	 */
	public void insert(MallSetting setting) throws Exception;
	
	/**
	 * 修改
	 * @param MallSetting
	 * @throws Exception
	 */
	public void update(MallSetting setting) throws Exception;
	
	/**
	 * 删除
	 * @param MallSetting
	 * @throws Exception
	 */
	public void delete(MallSetting setting) throws Exception;
}
