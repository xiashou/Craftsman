package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysRole;

public interface SysRoleService {

	/**
	 * 查询所有系统角色
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> getAll() throws Exception;
	
	/**
	 * 查询所有系统角色简略信息
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> getAllRoleSimple() throws Exception;
	
	/**
	 * 查询所有没有锁定的角色
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> getUnlockRole(Integer roleId) throws Exception;
	
	/**
	 * 根据角色ID查找角色
	 * @param roleId
	 * @return SysRole
	 * @throws Exception
	 */
	public SysRole getRoleById(Integer roleId) throws Exception;
	
	/**
	 * 根据多个角色ID查找角色
	 * @param roleId
	 * @return SysRole
	 * @throws Exception
	 */
	public List<SysRole> getNormalRoleById(Integer roleId) throws Exception;
	
	/**
	 * 添加角色
	 * @param SysRole
	 * @throws Exception
	 */
	public void insert(SysRole role) throws Exception;
	
	/**
	 * 修改角色
	 * @param SysRole
	 * @throws Exception
	 */
	public void update(SysRole role) throws Exception;
	
	/**
	 * 删除角色
	 * @param SysRole
	 * @throws Exception
	 */
	public void delete(SysRole role) throws Exception;
	
	/**
	 * 根据条件分页查找菜单
	 * @param menu, start,limit
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysRole> getListPage(SysRole role, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找菜单条目数
	 * @param menu
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public Integer getListCount(SysRole role) throws Exception;
}
