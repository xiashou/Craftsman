package com.tcode.business.basic.service;

import java.util.List;

import com.tcode.business.basic.model.BaseCarModel;

public interface BaseCarModelService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseCarModel> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return BaseCarModel
	 * @throws Exception
	 */
	public BaseCarModel getById(Integer id) throws Exception;
	
	/**
	 * 根据根据系列ID查询
	 * @param code
	 * @return List<BaseCarModel>
	 * @throws Exception
	 */
	public List<BaseCarModel> getBySeriesId(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param BaseCarModel
	 * @throws Exception
	 */
	public void insert(BaseCarModel carModel) throws Exception;
	
	/**
	 * 修改
	 * @param BaseCarModel
	 * @throws Exception
	 */
	public void update(BaseCarModel carModel) throws Exception;
	
	/**
	 * 删除
	 * @param BaseCarModel
	 * @throws Exception
	 */
	public void delete(BaseCarModel carModel) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param carModel, start, limit
	 * @return List<BaseCarBrand>
	 * @throws Exception
	 */
	public List<BaseCarModel> getListPage(BaseCarModel carModel, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<BaseCarModel>
	 * @throws Exception
	 */
	public Integer getListCount(BaseCarModel carModel) throws Exception;
}
