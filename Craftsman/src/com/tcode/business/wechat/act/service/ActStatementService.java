package com.tcode.business.wechat.act.service;

import com.tcode.business.wechat.act.model.ActStatement;

public interface ActStatementService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return ActStatement
	 * @throws Exception
	 */
	public ActStatement getById(String appId) throws Exception;
	
	/**
	 * 添加
	 * @param ActStatement
	 * @throws Exception
	 */
	public void insert(ActStatement statement) throws Exception;
	
	/**
	 * 修改
	 * @param ActStatement
	 * @throws Exception
	 */
	public void update(ActStatement statement) throws Exception;
	
	/**
	 * 删除
	 * @param ActStatement
	 * @throws Exception
	 */
	public void delete(ActStatement statement) throws Exception;
}
