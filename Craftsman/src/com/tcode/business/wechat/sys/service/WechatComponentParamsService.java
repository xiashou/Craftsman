package com.tcode.business.wechat.sys.service;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatComponentParams;

public interface WechatComponentParamsService {
	
	/**
	 * 添加
	 * @param wechatComponentParams
	 * @throws Exception
	 */
	public void insert(WechatComponentParams wechatComponentParams) throws Exception;
	
	/**
	 * 修改
	 * @param wechatComponentParams
	 * @throws Exception
	 */
	public void update(WechatComponentParams wechatComponentParams) throws Exception;
	
	/**
	 * 删除
	 * @param wechatComponentParams
	 * @throws Exception
	 */
	public void delete(WechatComponentParams wechatComponentParams) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatComponentParams> getAll() throws Exception;

}
