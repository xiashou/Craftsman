package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallBanner;

public interface MallBannerDao {

	/**
	 * 根据id查找
	 * @param id
	 * @throws Exception
	 */
	public MallBanner loadById(Integer id) throws Exception;
	
	/**
	 * 根据公众号id查找
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<MallBanner> loadByAppId(String appId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallBanner banner) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallBanner banner) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallBanner banner) throws Exception;
}
