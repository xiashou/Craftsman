package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysRoleMenu;

public interface SysRoleMenuService {

	/**
	 * 查询所有角色菜单关联
	 * @return List<SysRoleMenu>
	 * @throws Exception
	 */
	public List<SysRoleMenu> getAll() throws Exception;
	/**
	 * 根据角色ID查找角色菜单关联
	 * @param roleId
	 * @return List<SysRoleMenu>
	 * @throws Exception
	 */
	public List<SysRoleMenu> getByRoleId(Integer roleId) throws Exception;
	/**
	 * 根据菜单ID名查找角色菜单关联
	 * @param menuId
	 * @return List<SysRoleMenu>
	 * @throws Exception
	 */
	public List<SysRoleMenu> getByMenuId(String menuId) throws Exception;
	/**
	 * 根据角色ID 和 菜单ID名查找角色菜单关联
	 * @param menuId
	 * @return List<SysRoleMenu>
	 * @throws Exception
	 */
	public SysRoleMenu getByRoleIdAndMenuId(Integer roleId, String menuId) throws Exception;
	/**
	 * 添加角色菜单关联
	 * @param SysRoleMenu
	 * @throws Exception
	 */
	public boolean insert(SysRoleMenu roleMenu);
	/**
	 * 修改角色菜单关联
	 * @param SysRoleMenu
	 * @throws Exception
	 */
	public boolean update(SysRoleMenu roleMenu);
	/**
	 * 删除角色菜单关联
	 * @param SysRoleMenu
	 * @throws Exception
	 */
	public boolean delete(SysRoleMenu roleMenu);
	/**
	 * 根据角色ID删除角色菜单关联
	 * @param SysRoleMenu
	 * @throws Exception
	 */
	public boolean removeByRoleId(Integer roleId);
	
	
}
