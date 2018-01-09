package com.tcode.business.basic.dao;

import java.util.List;

import com.tcode.business.basic.model.BaseCarModel;

public interface BaseCarModelDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseCarModel> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return BaseCarModel
	 * @throws Exception
	 */
	public BaseCarModel loadById(Integer id) throws Exception;
	
	/**
	 * 根据简写代码查询
	 * @param code
	 * @return List<BaseCarModel>
	 * @throws Exception
	 */
	public List<BaseCarModel> loadBySeriesId(Integer seriesId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(BaseCarModel carModel) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(BaseCarModel carModel) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(BaseCarModel carModel) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<BaseCarModel>
	 * @throws Exception
	 */
	public List<BaseCarModel> loadListPage(BaseCarModel carModel, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(BaseCarModel carModel) throws Exception;
}
