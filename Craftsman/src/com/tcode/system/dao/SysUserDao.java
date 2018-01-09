package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysUser;

public interface SysUserDao {

	/**
	 * 查询所有系统用户
	 * @return List<SysUser>
	 * @throws Exception
	 */
	public List<SysUser> loadAll() throws Exception;
	
	/**
	 * 根据id查找用户
	 * @param id
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUser loadById(Integer id) throws Exception;
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUser loadByName(String userName) throws Exception;
	/**
	 * 管家端登录
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public SysUser loadByMgrName(String userName) throws Exception;
	/**
	 * 根据角色ID查找用户
	 * @param userName
	 * @return SysUser
	 * @throws Exception
	 */
	public List<SysUser> loadByRoleId(Integer roleId) throws Exception;
	
	/**
	 * 添加一名用户
	 * @throws Exception
	 */
	public void save(SysUser user) throws Exception;
	
	/**
	 * 更新用户
	 * @throws Exception
	 */
	public void edit(SysUser user) throws Exception;
	
	/**
	 * 删除用户
	 * @throws Exception
	 */
	public void remove(SysUser user) throws Exception;
	
	/**
	 * 根据条件分页查找系统用户
	 * @param user, start,limit
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysUser> loadListPage(SysUser user, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找系统用户条目数
	 * @param user
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(SysUser user) throws Exception;
	
}
