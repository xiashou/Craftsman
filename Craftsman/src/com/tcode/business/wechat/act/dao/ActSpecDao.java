package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.ActSpec;

public interface ActSpecDao {

	public ActSpec loadById(Integer id) throws Exception;
	
	public List<ActSpec> loadByActId(Integer actId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ActSpec spec) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ActSpec spec) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ActSpec spec) throws Exception;
}
