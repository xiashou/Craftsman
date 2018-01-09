package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysMenu;

public interface SysMenuService {

	/**
	 * 查询所有系统菜单
	 * @return
	 * @throws Exception
	 */
	public List<SysMenu> getAll() throws Exception;
	
	/**
	 * 根据菜单ID名查找菜单
	 * @param menuId
	 * @return SysMenu
	 * @throws Exception
	 */
	public SysMenu getMenuById(String id) throws Exception;
	
	/**
	 * 根据父ID名查找菜单
	 * @param parentId
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysMenu> getMenuByParentId(String parentId) throws Exception;
	
	/**
	 * 根据父ID和菜单类型查找
	 * @param parentId
	 * @param menuType
	 * @return
	 * @throws Exception
	 */
	public List<SysMenu> getMenuByMenuType(String parentId, String menuType) throws Exception;
	
	/**
	 * 根据父ID和角色ID名查找菜单
	 * @param parentId roleId
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysMenu> getMenuByParentIdAndRoleId(String parentId, Integer roleId, String menuType) throws Exception;
	
	/**
	 * 查询代理菜单
	 * @return
	 * @throws Exception
	 */
	public List<SysMenu> getMenuByAgent(String parentId) throws Exception;
	
	/**
	 * 递归完成代理菜单
	 * @param menuList
	 * @throws Exception
	 */
	public void completeAgentTree(String parentId, List<SysMenu> menuList) throws Exception;
	
	/**
	 * 递归完成常规菜单
	 * @param menuList
	 * @throws Exception
	 */
	public void completeMenuTree(List<SysMenu> menuList) throws Exception;
	
	/**
	 * 递归完成授权菜单
	 * @param menuList
	 * @throws Exception
	 */
	public void completeAuthorizationTree(Integer curRoleId, Integer roleId, String menuType, List<SysMenu> menuList) throws Exception;
	
	/**
	 * 递归完成角色菜单
	 * @param menuList
	 * @throws Exception
	 */
	public void completeRoleTree(Integer roleId, List<SysMenu> menuList, String menuType) throws Exception;
	
	/**
	 * 添加菜单
	 * @param SysMenu
	 * @throws Exception
	 */
	public void insert(SysMenu menu) throws Exception;
	
	/**
	 * 修改菜单
	 * @param SysMenu
	 * @throws Exception
	 */
	public void update(SysMenu menu) throws Exception;
	
	/**
	 * 删除菜单
	 * @param SysMenu
	 * @throws Exception
	 */
	public void delete(SysMenu menu) throws Exception;
	
	/**
	 * 根据条件分页查找菜单
	 * @param menu, start, limit
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysMenu> getListPage(SysMenu menu, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找菜单条目数
	 * @param menu
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public Integer getListCount(SysMenu menu) throws Exception;

}
