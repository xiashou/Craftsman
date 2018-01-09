package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysMenu;

public interface SysMenuDao {

	/**
	 * 查询所有系统用户
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysMenu> loadAll() throws Exception;
	
	/**
	 * 根据id查找菜单
	 * @param id
	 * @return SysMenu
	 * @throws Exception
	 */
	public SysMenu loadById(String id) throws Exception;
	
	/**
	 * 添加一条菜单
	 * @throws Exception
	 */
	public void save(SysMenu menu) throws Exception;
	
	/**
	 * 更新菜单
	 * @throws Exception
	 */
	public void edit(SysMenu menu) throws Exception;
	
	/**
	 * 删除菜单
	 * @throws Exception
	 */
	public void remove(SysMenu menu) throws Exception;
	
	/**
	 * 根据父id查找菜单
	 * @param parentId
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysMenu> loadByParentId(String id) throws Exception;
	
	/**
	 * 根据父ID和菜单类型查找菜单
	 * @param parentId
	 * @param menuType
	 * @return
	 * @throws Exception
	 */
	public List<SysMenu> loadByMenuType(String parentId, String menuType) throws Exception;
	
	/**
	 * 查询代理菜单
	 * @return
	 * @throws Exception
	 */
	public List<SysMenu> loadByAgent(String parentId) throws Exception;
	
	/**
	 * 根据父id及角色id查找菜单
	 * @param parentId
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysMenu> loadByParentIdAndRoleId(String parentId, Integer roleId, String menuType) throws Exception;
	
	/**
	 * 根据条件分页查找菜单
	 * @param menu, start,limit
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysMenu> loadListPage(SysMenu menu, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找菜单条目数
	 * @param menu
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(SysMenu menu) throws Exception;
	
	/**
	 * 根据父ID找出子节点中最大ID
	 * @param parentId
	 * @return Integer
	 * @throws Exception
	 */
	public String loadMaxIdByParentId(String parentId) throws Exception;
	
	/**
	 * 根据id查找父Id
	 * @param id
	 * @return parentId
	 * @throws Exception
	 */
	public String loadParentIdById(String id) throws Exception;
}
