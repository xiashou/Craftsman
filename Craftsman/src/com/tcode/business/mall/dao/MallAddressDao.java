package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallAddress;

public interface MallAddressDao {

	public MallAddress loadById(Integer id) throws Exception;
	
	public List<MallAddress> loadByMemId(Integer memId) throws Exception;
	
	public void cancelDefalue(Integer memId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallAddress address) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallAddress address) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallAddress address) throws Exception;
}
