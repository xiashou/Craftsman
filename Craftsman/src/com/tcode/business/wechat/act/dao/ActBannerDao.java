package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.ActBanner;

public interface ActBannerDao {

	/**
	 * 根据id查找
	 * @param id
	 * @throws Exception
	 */
	public ActBanner loadById(Integer id) throws Exception;
	
	/**
	 * 根据公众号id查找
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<ActBanner> loadByAppId(String appId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ActBanner banner) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ActBanner banner) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ActBanner banner) throws Exception;
}
