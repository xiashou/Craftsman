package com.tcode.business.basic.dao;

import java.util.List;

import com.tcode.business.basic.model.BaseCarBrand;

public interface BaseCarBrandDao {
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseCarBrand> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return BaseCarBrand
	 * @throws Exception
	 */
	public BaseCarBrand loadById(Integer id) throws Exception;
	
	/**
	 * 根据简写代码查询
	 * @param code
	 * @return List<BaseCarBrand>
	 * @throws Exception
	 */
	public List<BaseCarBrand> loadByCode(String code) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(BaseCarBrand carBrand) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(BaseCarBrand carBrand) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(BaseCarBrand carBrand) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<BaseCarBrand>
	 * @throws Exception
	 */
	public List<BaseCarBrand> loadListPage(BaseCarBrand carBrand, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(BaseCarBrand carBrand) throws Exception;

}
