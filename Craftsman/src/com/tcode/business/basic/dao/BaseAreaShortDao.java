package com.tcode.business.basic.dao;

import java.util.List;

import com.tcode.business.basic.model.BaseAreaShort;

public interface BaseAreaShortDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseAreaShort> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return BaseAreaShort
	 * @throws Exception
	 */
	public BaseAreaShort loadById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(BaseAreaShort areaShort) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(BaseAreaShort areaShort) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(BaseAreaShort areaShort) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<BaseAreaShort>
	 * @throws Exception
	 */
	public List<BaseAreaShort> loadListPage(BaseAreaShort areaShort, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(BaseAreaShort areaShort) throws Exception;
}
