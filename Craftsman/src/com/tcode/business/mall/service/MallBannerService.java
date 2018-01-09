package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallBanner;

public interface MallBannerService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return MallBanner
	 * @throws Exception
	 */
	public MallBanner getById(Integer id) throws Exception;
	
	/**
	 * 根据appId查找
	 * @param appid
	 * @return
	 * @throws Exception
	 */
	public List<MallBanner> getListByAppId(String appid) throws Exception;
	
	/**
	 * 添加
	 * @param MallBanner
	 * @throws Exception
	 */
	public void insert(MallBanner mallBrand) throws Exception;
	
	/**
	 * 修改
	 * @param MallBanner
	 * @throws Exception
	 */
	public void update(MallBanner mallBrand) throws Exception;
	
	/**
	 * 删除
	 * @param MallBanner
	 * @throws Exception
	 */
	public void delete(MallBanner mallBrand) throws Exception;
}
