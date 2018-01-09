package com.tcode.business.basic.service;

import java.util.List;

import com.tcode.business.basic.model.BaseCarBrand;

public interface BaseCarBrandService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseCarBrand> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return BaseCarBrand
	 * @throws Exception
	 */
	public BaseCarBrand getById(Integer id) throws Exception;
	
	/**
	 * 根据简写代码查询
	 * @param code
	 * @return List<BaseCarBrand>
	 * @throws Exception
	 */
	public List<BaseCarBrand> getByCode(String code) throws Exception;
	
	/**
	 * 添加
	 * @param BaseCarBrand
	 * @throws Exception
	 */
	public void insert(BaseCarBrand carBrand) throws Exception;
	
	/**
	 * 修改
	 * @param BaseCarBrand
	 * @throws Exception
	 */
	public void update(BaseCarBrand carBrand) throws Exception;
	
	/**
	 * 删除
	 * @param BaseCarBrand
	 * @throws Exception
	 */
	public void delete(BaseCarBrand carBrand) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start,limit
	 * @return List<BaseCarBrand>
	 * @throws Exception
	 */
	public List<BaseCarBrand> getListPage(BaseCarBrand carBrand, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<BaseCarBrand>
	 * @throws Exception
	 */
	public Integer getListCount(BaseCarBrand carBrand) throws Exception;
}
