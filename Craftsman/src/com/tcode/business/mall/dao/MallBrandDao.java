package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallBrand;


public interface MallBrandDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallBrand> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MallBrand
	 * @throws Exception
	 */
	public MallBrand loadById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallBrand mallBrand) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallBrand mallBrand) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallBrand mallBrand) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param mallBrand, start,limit
	 * @return List<MallBrand>
	 * @throws Exception
	 */
	public List<MallBrand> loadListPage(MallBrand mallBrand, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param mallBrand
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(MallBrand mallBrand) throws Exception;
}
