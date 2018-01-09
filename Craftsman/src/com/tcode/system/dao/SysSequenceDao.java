package com.tcode.system.dao;

import java.util.List;

import com.tcode.system.model.SysSequence;

public interface SysSequenceDao {

	/**
	 * 查询所有
	 * @return List<SysSequence>
	 * @throws Exception
	 */
	public List<SysSequence> loadAll() throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return SysSequence
	 * @throws Exception
	 */
	public SysSequence loadById(Integer id) throws Exception;
	/**
	 * 添加参数
	 * @throws Exception
	 */
	public void save(SysSequence sequence) throws Exception;
	
	/**
	 * 更新参数
	 * @throws Exception
	 */
	public void edit(SysSequence sequence) throws Exception;
	
	/**
	 * 删除参数
	 * @throws Exception
	 */
	public void remove(SysSequence sequence) throws Exception;
	
	/**
	 * 根据条件分页查找系统参数
	 * @param param, start, limit
	 * @return List<SysSequence>
	 * @throws Exception
	 */
	public List<SysSequence> loadListPage(SysSequence sequence, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找系统参数条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(SysSequence sequence) throws Exception;
	
	/**
	 * 根据fieldName 查找
	 * @param key
	 * @return SysSequence
	 * @throws Exception
	 */
	public SysSequence loadByName(String fieldName) throws Exception;
}
