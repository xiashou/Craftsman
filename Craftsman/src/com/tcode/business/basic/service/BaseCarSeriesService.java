package com.tcode.business.basic.service;

import java.util.List;

import com.tcode.business.basic.model.BaseCarSeries;

public interface BaseCarSeriesService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseCarSeries> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return BaseCarSeries
	 * @throws Exception
	 */
	public BaseCarSeries getById(Integer id) throws Exception;
	
	/**
	 * 根据车ID取系列信息
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public BaseCarSeries getByCarId(Integer carId) throws Exception;
	
	/**
	 * 根据品牌ID查询
	 * @param id
	 * @return List<BaseCarSeries>
	 * @throws Exception
	 */
	public List<BaseCarSeries> getByBrandId(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param BaseCarSeries
	 * @throws Exception
	 */
	public void insert(BaseCarSeries carSeries) throws Exception;
	
	/**
	 * 修改
	 * @param BaseCarSeries
	 * @throws Exception
	 */
	public void update(BaseCarSeries carSeries) throws Exception;
	
	/**
	 * 删除
	 * @param BaseCarSeries
	 * @throws Exception
	 */
	public void delete(BaseCarSeries carSeries) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param carModel, start, limit
	 * @return List<BaseCarSeries>
	 * @throws Exception
	 */
	public List<BaseCarSeries> getListPage(BaseCarSeries carSeries, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<BaseCarSeries>
	 * @throws Exception
	 */
	public Integer getListCount(BaseCarSeries carSeries) throws Exception;
}
