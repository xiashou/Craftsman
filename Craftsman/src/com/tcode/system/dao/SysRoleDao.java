package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysRole;

public interface SysRoleDao {

	/**
	 * 查询所有系统角色
	 * @return List<SysRole>
	 * @throws Exception
	 */
	public List<SysRole> loadAll() throws Exception;
	
	/**
	 * 查询所有系统角色简略信息（id+name）
	 * @return List<SysRole>
	 * @throws Exception
	 */
	public List<SysRole> loadAllSimple() throws Exception;
	
	/**
	 * 查询所有没有锁定的角色
	 * @return List<SysRole>
	 * @throws Exception
	 */
	public List<SysRole> loadUnlockRole() throws Exception;
	
	/**
	 * 根据id查找角色
	 * @param id
	 * @return SysRole
	 * @throws Exception
	 */
	public SysRole loadById(Integer roleId) throws Exception;
	
	/**
	 * 根据多id查找角色
	 * @param id
	 * @return SysRole
	 * @throws Exception
	 */
	public List<SysRole> loadNormalById(Integer roleId) throws Exception;
	
	/**
	 * 添加角色
	 * @throws Exception
	 */
	public void save(SysRole role) throws Exception;
	
	/**
	 * 更新角色
	 * @throws Exception
	 */
	public void edit(SysRole role) throws Exception;
	
	/**
	 * 删除角色
	 * @throws Exception
	 */
	public void remove(SysRole role) throws Exception;
	
	/**
	 * 根据条件分页查找系统角色
	 * @param role, start,limit
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysRole> loadListPage(SysRole role, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找系统角色条目数
	 * @param role
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(SysRole role) throws Exception;
	
}
