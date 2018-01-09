package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysAgent;

public interface SysAgentDao {

	/**
	 * 查询所有系统代理
	 * @return List<SysAgent>
	 * @throws Exception
	 */
	public List<SysAgent> loadAll() throws Exception;
	
	/**
	 * 根据id查找代理
	 * @param id
	 * @return SysAgent
	 * @throws Exception
	 */
	public SysAgent loadById(Integer id) throws Exception;
	/**
	 * 根据用户名查找用户
	 * @param agentName
	 * @return SysAgent
	 * @throws Exception
	 */
	public SysAgent loadByName(String agentName) throws Exception;
	/**
	 * 根据地区ID查找代理
	 * @param areaId
	 * @return SysAgent
	 * @throws Exception
	 */
	public List<SysAgent> loadByAreaId(String areaId) throws Exception;
	
	/**
	 * 添加一名代理
	 * @throws Exception
	 */
	public void save(SysAgent agent) throws Exception;
	
	/**
	 * 更新代理
	 * @throws Exception
	 */
	public void edit(SysAgent agent) throws Exception;
	
	/**
	 * 删除代理
	 * @throws Exception
	 */
	public void remove(SysAgent agent) throws Exception;
	
	
}
