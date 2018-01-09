package com.tcode.business.wechat.act.service;

import java.util.List;

import com.tcode.business.wechat.act.model.ActBanner;

public interface ActBannerService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return ActBanner
	 * @throws Exception
	 */
	public ActBanner getById(Integer id) throws Exception;
	
	/**
	 * 根据appId查找
	 * @param appid
	 * @return
	 * @throws Exception
	 */
	public List<ActBanner> getListByAppId(String appid) throws Exception;
	
	/**
	 * 添加
	 * @param ActBanner
	 * @throws Exception
	 */
	public void insert(ActBanner banner) throws Exception;
	
	/**
	 * 修改
	 * @param ActBanner
	 * @throws Exception
	 */
	public void update(ActBanner banner) throws Exception;
	
	/**
	 * 删除
	 * @param ActBanner
	 * @throws Exception
	 */
	public void delete(ActBanner banner) throws Exception;
}
