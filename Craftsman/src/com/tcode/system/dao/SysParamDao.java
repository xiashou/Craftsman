package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysParam;

public interface SysParamDao {

	/**
	 * 查询所有系统参数
	 * @return List<SysParam>
	 * @throws Exception
	 */
	public List<SysParam> loadAll() throws Exception;
	
	/**
	 * 根据id查找参数
	 * @param id
	 * @return SysParam
	 * @throws Exception
	 */
	public SysParam loadById(Integer id) throws Exception;
	/**
	 * 添加参数
	 * @throws Exception
	 */
	public void save(SysParam param) throws Exception;
	
	/**
	 * 更新参数
	 * @throws Exception
	 */
	public void edit(SysParam param) throws Exception;
	
	/**
	 * 删除参数
	 * @throws Exception
	 */
	public void remove(SysParam param) throws Exception;
	
	/**
	 * 根据条件分页查找系统参数
	 * @param param, start,limit
	 * @return List<SysMenu>
	 * @throws Exception
	 */
	public List<SysParam> loadListPage(SysParam param, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找系统参数条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(SysParam param) throws Exception;
	
	/**
	 * 根据key查找参数
	 * @param key
	 * @return SysParam
	 * @throws Exception
	 */
	public SysParam loadByKey(String key) throws Exception;
}
