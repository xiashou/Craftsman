package com.tcode.business.wechat.sys.dao;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatComponentParams;

public interface WechatComponentParamsDao {
	
	/**
	 * 添加
	 * @param wechatComponentParams
	 * @throws Exception
	 */
	public void save(WechatComponentParams wechatComponentParams) throws Exception;
	
	/**
	 * 修改
	 * @param wechatComponentParams
	 * @throws Exception
	 */
	public void edit(WechatComponentParams wechatComponentParams) throws Exception;
	
	/**
	 * 删除
	 * @param wechatComponentParams
	 * @throws Exception
	 */
	public void remove(WechatComponentParams wechatComponentParams) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatComponentParams> loadAll() throws Exception;

}
