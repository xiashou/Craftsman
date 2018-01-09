package com.tcode.business.wechat.act.service;

import java.util.List;

import com.tcode.business.wechat.act.model.ActSpec;

public interface ActSpecService {

	public ActSpec getById(Integer id) throws Exception;
	
	public List<ActSpec> getListByActId(Integer actId) throws Exception;
	
	/**
	 * 添加
	 * @param ActSpec
	 * @throws Exception
	 */
	public void insert(ActSpec spec) throws Exception;
	
	/**
	 * 修改
	 * @param ActSpec
	 * @throws Exception
	 */
	public void update(ActSpec spec) throws Exception;
	
	/**
	 * 删除
	 * @param ActSpec
	 * @throws Exception
	 */
	public void delete(ActSpec spec) throws Exception;
}
