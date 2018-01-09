package com.tcode.system.service;

import java.util.List;

import com.tcode.system.model.SysSequence;

public interface SysSequenceService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<SysSequence> getAll() throws Exception;
	
	/**
	 * 根据参数ID查找
	 * @param id
	 * @return SysSequence
	 * @throws Exception
	 */
	public SysSequence getSequenceById(Integer id) throws Exception;
	
	/**
	 * 根据paramKey查询
	 * @param paramKey
	 * @return SysSequence
	 * @throws Exception
	 */
	public SysSequence getSequenceByName(String fieldName) throws Exception;
	
	/**
	 * 添加
	 * @param SysSequence
	 * @throws Exception
	 */
	public void insert(SysSequence sequence) throws Exception;
	
	/**
	 * 修改
	 * @param SysSequence
	 * @throws Exception
	 */
	public void update(SysSequence sequence) throws Exception;
	
	/**
	 * 删除
	 * @param SysSequence
	 * @throws Exception
	 */
	public void delete(SysSequence sequence) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start, limit
	 * @return List<SysSequence>
	 * @throws Exception
	 */
	public List<SysSequence> getListPage(SysSequence sequence, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return List<SysSequence>
	 * @throws Exception
	 */
	public Integer getListCount(SysSequence sequence) throws Exception;
	
}
