package com.tcode.business.basic.dao;

import java.util.List;

import com.tcode.business.basic.model.BaseCarSeries;

public interface BaseCarSeriesDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseCarSeries> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return BaseCarSeries
	 * @throws Exception
	 */
	public BaseCarSeries loadById(Integer id) throws Exception;
	
	/**
	 * 根据车ID取系列查找
	 * @param carId
	 * @return BaseCarSeries
	 * @throws Exception
	 */
	public BaseCarSeries loadByCarId(Integer carId) throws Exception;
	
	/**
	 * 根据简写代码查询
	 * @param code
	 * @return List<BaseCarSeries>
	 * @throws Exception
	 */
	public List<BaseCarSeries> loadByBrandId(Integer brandId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(BaseCarSeries carSeries) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(BaseCarSeries carSeries) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(BaseCarSeries carSeries) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<BaseCarSeries>
	 * @throws Exception
	 */
	public List<BaseCarSeries> loadListPage(BaseCarSeries carSeries, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(BaseCarSeries carSeries) throws Exception;
}
