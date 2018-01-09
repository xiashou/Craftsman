package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysButton;

public interface SysButtonService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysButton> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return SysButton
	 * @throws Exception
	 */
	public SysButton getButtonById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param SysButton
	 * @throws Exception
	 */
	public void insert(SysButton button) throws Exception;
	
	/**
	 * 修改
	 * @param SysButton
	 * @throws Exception
	 */
	public void update(SysButton button) throws Exception;
	
	/**
	 * 删除
	 * @param SysButton
	 * @throws Exception
	 */
	public void delete(SysButton button) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start,limit
	 * @return List<SysButton>
	 * @throws Exception
	 */
	public List<SysButton> getListPage(SysButton button, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<SysButton>
	 * @throws Exception
	 */
	public Integer getListCount(SysButton button) throws Exception;
}
