package com.tcode.business.wechat.act.dao;

import com.tcode.business.wechat.act.model.ActStatement;

public interface ActStatementDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return ActStatement
	 * @throws Exception
	 */
	public ActStatement loadById(String appId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ActStatement statement) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ActStatement statement) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ActStatement statement) throws Exception;
}
