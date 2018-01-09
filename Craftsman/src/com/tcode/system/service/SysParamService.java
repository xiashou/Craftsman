package com.tcode.system.service;

import java.util.List;
import java.util.Map;

import com.tcode.system.model.SysParam;

public interface SysParamService {

	/**
	 * 查询所有系统参数
	 * @return
	 * @throws Exception
	 */
	public List<SysParam> getAll() throws Exception;
	
	/**
	 * 根据参数ID查找参数
	 * @param id
	 * @return SysParam
	 * @throws Exception
	 */
	public SysParam getParamById(Integer id) throws Exception;
	
	/**
	 * 查找所有系统参数组成Map
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getSysParamMap() throws Exception;
	
	/**
	 * 添加参数
	 * @param SysParam
	 * @throws Exception
	 */
	public void insert(SysParam param) throws Exception;
	
	/**
	 * 修改参数
	 * @param SysParam
	 * @throws Exception
	 */
	public void update(SysParam param) throws Exception;
	
	/**
	 * 删除参数
	 * @param SysParam
	 * @throws Exception
	 */
	public void delete(SysParam param) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param param, start,limit
	 * @return List<SysParam>
	 * @throws Exception
	 */
	public List<SysParam> getListPage(SysParam param, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param param
	 * @return List<SysParam>
	 * @throws Exception
	 */
	public Integer getListCount(SysParam param) throws Exception;
	
	/**
	 * 根据paramKey查询参数
	 * @param paramKey
	 * @return SysParam
	 * @throws Exception
	 */
	public SysParam getParamByKey(String paramKey) throws Exception;
}
