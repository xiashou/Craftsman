package com.tcode.business.basic.service;

import java.util.List;

import com.tcode.business.basic.model.BaseAreaShort;

public interface BaseAreaShortService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseAreaShort> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return BaseAreaShort
	 * @throws Exception
	 */
	public BaseAreaShort getById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param BaseAreaShort
	 * @throws Exception
	 */
	public void insert(BaseAreaShort areaShort) throws Exception;
	
	/**
	 * 修改
	 * @param BaseAreaShort
	 * @throws Exception
	 */
	public void update(BaseAreaShort areaShort) throws Exception;
	
	/**
	 * 删除
	 * @param BaseAreaShort
	 * @throws Exception
	 */
	public void delete(BaseAreaShort areaShort) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start,limit
	 * @return List<BaseAreaShort>
	 * @throws Exception
	 */
	public List<BaseAreaShort> getListPage(BaseAreaShort areaShort, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<BaseAreaShort>
	 * @throws Exception
	 */
	public Integer getListCount(BaseAreaShort areaShort) throws Exception;
}
