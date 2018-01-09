package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysRoleMenu;

public interface SysRoleMenuDao {

	/**
	 * 查询所有角色菜单关联
	 * @return List<SysRoleMenu>
	 * @throws Exception
	 */
	public List<SysRoleMenu> loadAll() throws Exception;
	/**
	 * 根据角色ID查找角色菜单关联
	 * @param roleId
	 * @return SysRoleMenu
	 * @throws Exception
	 */
	public List<SysRoleMenu> loadByRoleId(Integer roleId) throws Exception;
	/**
	 * 根据菜单ID名查找角色菜单关联
	 * @param menuId
	 * @return SysRoleMenu
	 * @throws Exception
	 */
	public List<SysRoleMenu> loadByMenuId(String menuId) throws Exception;
	/**
	 * 根据角色ID和菜单ID名查找角色菜单关联
	 * @param menuId
	 * @return SysRoleMenu
	 * @throws Exception
	 */
	public SysRoleMenu loadByRoleIdAndMenuId(Integer roleId, String menuId) throws Exception;
	/**
	 * 添加角色菜单关联
	 * @throws Exception
	 */
	public void save(SysRoleMenu roleMenu) throws Exception;
	/**
	 * 更新角色菜单关联
	 * @throws Exception
	 */
	public void edit(SysRoleMenu roleMenu) throws Exception;
	/**
	 * 删除角色菜单关联
	 * @throws Exception
	 */
	public void remove(SysRoleMenu roleMenu) throws Exception;
	/**
	 * 根据roleMenuList删除角色菜单关联
	 * @throws Exception
	 */
	public void deleteByList(List<SysRoleMenu> roleMenuList) throws Exception;
	
}
