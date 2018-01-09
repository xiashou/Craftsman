package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysAgent;

public interface SysAgentService {

	/**
	 * 查询所有系统代理
	 * @return
	 * @throws Exception
	 */
	public List<SysAgent> getAll() throws Exception;
	
	/**
	 * 根据用户名查找代理
	 * @param agentId
	 * @return SysAgent
	 * @throws Exception
	 */
	public SysAgent getAgentById(Integer agentId) throws Exception;
	
	/**
	 * 根据用户名查找代理
	 * @param agentName
	 * @return
	 * @throws Exception
	 */
	public SysAgent getAgentByName(String agentName) throws Exception;
	
	/**
	 * 根据角色ID查找代理
	 * @param areaId
	 * @return
	 * @throws Exception
	 */
	public List<SysAgent> getAgentByAreaId(String areaId) throws Exception;
	
	/**
	 * 添加代理
	 * @param SysAgent
	 * @throws Exception
	 */
	public void insert(SysAgent agent) throws Exception;
	
	/**
	 * 修改代理
	 * @param SysAgent
	 * @throws Exception
	 */
	public void update(SysAgent agent) throws Exception;
	
	/**
	 * 删除代理
	 * @param SysAgent
	 * @throws Exception
	 */
	public void delete(SysAgent agent) throws Exception;
	
}
