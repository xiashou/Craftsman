package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysButton;

public interface SysButtonDao {

	/**
	 * 查询所有
	 * @return List<SysButton>
	 * @throws Exception
	 */
	public List<SysButton> loadAll() throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return SysButton
	 * @throws Exception
	 */
	public SysButton loadById(Integer id) throws Exception;
	
	/**
	 * 根据菜单id查找
	 * @param id
	 * @return List<SysButton>
	 * @throws Exception
	 */
	public List<SysButton> loadByMenuId(String id) throws Exception;
	
	/**
	 * 根据菜单id和角色id查找
	 * @param id
	 * @return List<SysButton>
	 * @throws Exception
	 */
	public List<SysButton> loadByMenuIdAndRoleId(String id, Integer roleId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(SysButton button) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(SysButton button) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(SysButton button) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<SysButton>
	 * @throws Exception
	 */
	public List<SysButton> loadListPage(SysButton button, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(SysButton button) throws Exception;
}
