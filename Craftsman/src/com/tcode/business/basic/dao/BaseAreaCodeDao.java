package com.tcode.business.basic.dao;

import java.util.List;

import com.tcode.business.basic.model.BaseAreaCode;

public interface BaseAreaCodeDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseAreaCode> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param areaId
	 * @return BaseAreaCode
	 * @throws Exception
	 */
	public BaseAreaCode loadById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(BaseAreaCode areaCode) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(BaseAreaCode areaCode) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(BaseAreaCode areaCode) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<BaseAreaCode>
	 * @throws Exception
	 */
	public List<BaseAreaCode> loadListPage(BaseAreaCode areaCode, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(BaseAreaCode areaCode) throws Exception;
}
