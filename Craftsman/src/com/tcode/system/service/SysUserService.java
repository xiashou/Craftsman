package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysUser;

public interface SysUserService {

	/**
	 * 查询所有系统用户
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getAll() throws Exception;
	
	/**
	 * 根据用户名查找用户
	 * @param userId
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUser getUserById(Integer userId) throws Exception;
	
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public SysUser getUserByName(String userName) throws Exception;
	
	/**
	 * 管家端登录
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public SysUser getMgrByName(String userName) throws Exception;
	
	/**
	 * 根据角色ID查找用户
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> getUserByRoleId(Integer roleId) throws Exception;
	
	/**
	 * 添加用户
	 * @param SysUser
	 * @throws Exception
	 */
	public void insert(SysUser user) throws Exception;
	
	/**
	 * 修改用户
	 * @param SysUser
	 * @throws Exception
	 */
	public void update(SysUser user) throws Exception;
	
	/**
	 * 删除用户
	 * @param SysUser
	 * @throws Exception
	 */
	public void delete(SysUser user) throws Exception;
	
	/**
	 * 根据条件分页查找菜单
	 * @param menu, start,limit
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysUser> getListPage(SysUser user, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找菜单条目数
	 * @param menu
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public Integer getListCount(SysUser user) throws Exception;
}
